package io.ssafy.soupapi.domain.chat.application;

import io.ssafy.soupapi.domain.chat.dao.RChatRepository;
import io.ssafy.soupapi.domain.chat.dto.RChatMessage;
import io.ssafy.soupapi.domain.chat.dto.request.ChatMessageReq;
import io.ssafy.soupapi.domain.chat.dto.response.ChatMessageRes;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.noti.application.NotiService;
import io.ssafy.soupapi.domain.noti.dao.NotiRepository;
import io.ssafy.soupapi.domain.noti.dto.response.NewNotiRes;
import io.ssafy.soupapi.domain.noti.entity.MNoti;
import io.ssafy.soupapi.domain.project.mongodb.dao.MProjectRepository;
import io.ssafy.soupapi.domain.project.mongodb.entity.ChatMessage;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.util.DateConverterUtil;
import io.ssafy.soupapi.global.util.FindEntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final MProjectRepository mProjectRepository;
    private final RChatRepository rChatRepository;
    private final NotiService notiService;
    private final NotiRepository notiRepository;
    private final FindEntityUtil findEntityUtil;

    // 대화 저장
    @Transactional
    public ChatMessageRes saveMessage(String chatroomId, ChatMessageReq chatMessageReq) {
        long sentAtLong = System.currentTimeMillis();
        Instant sentAtInstant = Instant.ofEpochMilli(sentAtLong);
//        LocalDateTime sentAtLdt = Instant.ofEpochMilli(sentAtLong).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        String chatMessageId = UUID.randomUUID().toString();
        RChatMessage RChatMessage = chatMessageReq.toRChatMessage(chatMessageId, sentAtInstant);
        ChatMessage chatMessage = chatMessageReq.toMChatMessage(chatMessageId, sentAtInstant);

        // 1. 채팅 메시지 -> MongoDB 저장
        mProjectRepository.addChatMessage(new ObjectId(chatroomId), chatMessage);

        // 2. 태그 알림
        List<MNoti> mNotiList = new ArrayList<>();

        for (String mentioneeId : chatMessageReq.mentionedMemberIds()) {
//            RMentionNoti RMentionNoti = notiService.generateMentionNotiRedis(
//                    RChatMessage.chatMessageId(), chatMessageReq.sender().getMemberId(), mentioneeId
//            );

            MNoti mNoti = notiService.generateMNoti(chatroomId, chatMessageId, chatMessageReq, mentioneeId, sentAtInstant);
            mNotiList.add(mNoti);
        }

        // 2-1. 태그 알림 -> MongoDb 저장
        notiRepository.saveAll(mNotiList);

        // 2-2. 태그 알림 -> Redis 저장
//            notiService.saveMentionNotiToRedis(chatroomId, RMentionNoti, sentAtLong);

        // 2-3. SSE 알림 전송
        for (MNoti mNoti : mNotiList) {
            NewNotiRes newNotiRes = notiService.generateNewNotiRes(mNoti, chatMessageReq.sender().getProfileImageUrl());
            notiService.notify(mNoti.getReceiverId(), newNotiRes, "mention");
        }

        // 3. 채팅 메시지 -> Redis 저장
        rChatRepository.saveMessageToRedis(chatroomId, RChatMessage, sentAtLong);

        return generateChatMessageRes(RChatMessage.chatMessageId(), chatMessageReq, sentAtInstant);
    }

    public List<ChatMessageRes> getChatMessages(String chatroomId, PageOffsetRequest pageOffsetRequest, LocalDateTime standardTime) {
        List<ChatMessageRes> result = new ArrayList<>();
        List<RChatMessage> rChatMessageList;
        List<ChatMessage> mChatMessageList;
        Map<String, Member> senderMap = new HashMap<>();

        Long reqTime = DateConverterUtil.ldtToLong(standardTime);
        long offset = pageOffsetRequest.calculateOffset();
        rChatMessageList = rChatRepository.getNMessagesBefore(chatroomId, reqTime, offset, pageOffsetRequest.size());
//        log.info("getChatMessages() -> redis에서 {}개 획득", rChatMessageList.size());

        for (RChatMessage rChatMessage : rChatMessageList) {
            senderMap.put(rChatMessage.senderId(), null);
            result.add(rChatMessage.toChatMessageRes());
        }

        // MongoDB에, redis에서 발견한 earliest 메시지 이전에 발행된 메시지가 있는지 조회
        int rDataSize = rChatMessageList.size();
        if (rDataSize < pageOffsetRequest.size()) {
            int mDataSize = pageOffsetRequest.size() - rDataSize;

            Instant rLdt;
            if (rDataSize == 0) {
                rLdt = DateConverterUtil.kstLdtToInstant(standardTime);
            } else {
                rLdt = rChatMessageList.get(0).sentAt(); // redis에서 earliest 메시지의 sentAt
            }

            mChatMessageList = mProjectRepository.getNChatMessagesBefore(chatroomId, rLdt, mDataSize);
//            log.info("getChatMessages() -> mongoDB에서 {}개 획득", mChatMessageList.size());

            for (ChatMessage mChatMessage : mChatMessageList) {
                senderMap.put(mChatMessage.getSenderId(), null);
                result.add(mChatMessage.toGetChatMessageRes());
            }

            // MongoDB에서 조회한 데이터는 Redis에 저장 (캐싱)
            List<RChatMessage> rChatMessageToSave = new ArrayList<>();
            for (ChatMessage mChatMessage : mChatMessageList) {
                rChatMessageToSave.add(mChatMessage.toRChatMessage());
            }
            rChatRepository.saveMessagesToRedis(chatroomId, rChatMessageToSave, null);

        }

        for (String memberId : senderMap.keySet()) {
            Member member = findEntityUtil.findMemberById(UUID.fromString(memberId));
            senderMap.put(memberId, member);
        }

        for (ChatMessageRes res : result) {
            String senderId = res.getSender().getMemberId();
            res.getSender().setNickname(senderMap.get(senderId).getNickname());
            res.getSender().setProfileImageUrl(senderMap.get(senderId).getProfileImageUrl());
        }

        return result;
    }

    private ChatMessageRes generateChatMessageRes(
            String chatMessageId, ChatMessageReq chatMessageReq, Instant sentAt
    ) {
        return ChatMessageRes.builder()
                .chatMessageId(chatMessageId)
                .sender(chatMessageReq.sender())
                .message(chatMessageReq.message())
                .mentionedMemberIds(chatMessageReq.mentionedMemberIds())
                .sentAt(DateConverterUtil.instantToKstZdt(sentAt))
                .build();
    }

}
