package io.ssafy.soupapi.domain.chat.application;

import io.ssafy.soupapi.domain.chat.dao.RChatRepository;
import io.ssafy.soupapi.domain.chat.entity.RChatMessage;
import io.ssafy.soupapi.domain.chat.dto.request.ChatMessageReq;
import io.ssafy.soupapi.domain.chat.dto.response.ChatMessageRes;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.noti.application.NotiService;
import io.ssafy.soupapi.domain.noti.dao.NotiRepository;
import io.ssafy.soupapi.domain.noti.dao.RNotiRepository;
import io.ssafy.soupapi.domain.noti.dto.RMentionNoti;
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
    private final RNotiRepository rNotiRepository;
    private final FindEntityUtil findEntityUtil;

    // 대화 저장
    @Transactional
    public ChatMessageRes saveMessage(String chatroomId, ChatMessageReq chatMessageReq) {
        Long sentAtLong = System.currentTimeMillis();
        Instant sentAtInstant = Instant.ofEpochMilli(sentAtLong);

        // 채팅 메시지
        String chatMessageId = UUID.randomUUID().toString();
        RChatMessage rChatMessage = chatMessageReq.toRChatMessage(chatMessageId, sentAtInstant);
        ChatMessage chatMessage = chatMessageReq.toMChatMessage(chatMessageId, sentAtInstant);

        // 1. 채팅 메시지 -> MongoDB 저장
        mProjectRepository.addChatMessage(new ObjectId(chatroomId), chatMessage);

        // 태그 알림
        List<MNoti> mNotiList = new ArrayList<>();
        for (String mentioneeId : chatMessageReq.mentionedMemberIds()) {
            MNoti mNoti = notiService.generateMNoti(chatroomId, chatMessageId, chatMessageReq, mentioneeId, sentAtInstant);
            mNotiList.add(mNoti);
        }

        // 2. 태그 알림 -> MongoDb 저장
        notiRepository.saveAll(mNotiList);

        // 3. 태그 알림 -> Redis 저장
        rNotiRepository.saveNotisToRedis(mNotiList);

        // 4. 태그 알림 -> SSE 알림 전송
        for (MNoti mNoti : mNotiList) {
            String projectName = findEntityUtil.findPProjectById(mNoti.getProjectId()).getName();
            NewNotiRes newNotiRes = mNoti.generateNewNotiRes(
                chatMessageReq.sender().getProfileImageUrl(), projectName
            );
            notiService.notify(mNoti.getReceiverId(), newNotiRes, "mention");
        }

        // 5. 채팅 메시지 -> Redis 저장
        rChatRepository.insertMessage(chatroomId, rChatMessage);

        return generateChatMessageRes(rChatMessage.chatMessageId(), chatMessageReq, sentAtInstant);
    }

    public List<ChatMessageRes> getChatMessages(String chatroomId, PageOffsetRequest pageOffsetRequest, LocalDateTime standardTime) {
        List<ChatMessageRes> result = new ArrayList<>();
        List<RChatMessage> rChatMessageList = new ArrayList<>();
        List<ChatMessage> mChatMessageList = new ArrayList<>();
        Map<String, Member> senderMap = new HashMap<>();

        Long reqTime = DateConverterUtil.ldtToLong(standardTime);
        long offset = pageOffsetRequest.calculateOffset();

        findChatMessageFromRedis(
            rChatMessageList, senderMap, result,
            chatroomId, reqTime, offset, pageOffsetRequest.size()
        );

        // MongoDB에, redis에서 발견한 earliest 메시지 이전에 발행된 메시지가 있는지 조회
        int rDataSize = rChatMessageList.size();
        if (rDataSize < pageOffsetRequest.size()) {
            int mDataSize = pageOffsetRequest.size() - rDataSize;

            Instant rLdt;
            if (rDataSize == 0) rLdt = DateConverterUtil.kstLdtToInstant(standardTime);
            else rLdt = rChatMessageList.get(0).sentAt(); // redis에서 earliest 메시지의 sentAt

            findChatMessageFromMongodb(
                mChatMessageList, senderMap, result,
                chatroomId, rLdt, mDataSize
            );
        }

        List<UUID> senderIdList = senderMap.keySet().stream()
            .map(UUID::fromString)
            .toList();
        senderMap = findEntityUtil.findAllMemberByIdAndGenerateMap(senderIdList);

        for (ChatMessageRes res : result) {
            String senderId = res.getSender().getMemberId();
            res.getSender().setNickname(senderMap.get(senderId).getNickname());
            res.getSender().setProfileImageUrl(senderMap.get(senderId).getProfileImageUrl());
        }

        return result;
    }

    public void findChatMessageFromRedis(
            List<RChatMessage> rChatMessageList, Map<String, Member> senderMap, List<ChatMessageRes> result,
            String chatroomId, Long reqTime, long pageOffset, int pageSize
    ) {
        rChatMessageList = rChatRepository.getNMessagesBefore(chatroomId, reqTime, pageOffset, pageSize);
        log.info("getChatMessages() -> redis에서 {}개 획득", rChatMessageList.size());

        for (RChatMessage rChatMessage : rChatMessageList) {
            senderMap.put(rChatMessage.senderId(), null);
            result.add(rChatMessage.toChatMessageRes());
        }
    }

    private void findChatMessageFromMongodb(
            List<ChatMessage> mChatMessageList, Map<String, Member> senderMap, List<ChatMessageRes> result,
            String chatroomId, Instant beforeTime, int mDataSize
    ) {
        mChatMessageList = mProjectRepository.getNChatMessagesBefore(chatroomId, beforeTime, mDataSize);
        log.info("getChatMessages() -> mongoDB에서 {}개 획득", mChatMessageList.size());

        for (ChatMessage mChatMessage : mChatMessageList) {
            senderMap.put(mChatMessage.getSenderId(), null);
            result.add(mChatMessage.toGetChatMessageRes());
        }

        // MongoDB에서 조회한 데이터는 Redis에 저장 (캐싱)
        List<RChatMessage> rChatMessagesToSave = new ArrayList<>();
        for (ChatMessage mChatMessage : mChatMessageList) {
            rChatMessagesToSave.add(mChatMessage.toRChatMessage());
        }
        rChatRepository.insertMessages(chatroomId, rChatMessagesToSave);
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
