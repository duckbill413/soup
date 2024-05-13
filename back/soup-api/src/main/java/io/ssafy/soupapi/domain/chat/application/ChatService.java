package io.ssafy.soupapi.domain.chat.application;

import io.ssafy.soupapi.domain.chat.dao.RChatRepository;
import io.ssafy.soupapi.domain.chat.dto.RChatMessage;
import io.ssafy.soupapi.domain.chat.dto.request.ChatMessageReq;
import io.ssafy.soupapi.domain.chat.dto.response.ChatMessageRes;
import io.ssafy.soupapi.domain.chat.dto.response.GetChatMessageRes;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.noti.application.NotiService;
import io.ssafy.soupapi.domain.noti.dto.RMentionNoti;
import io.ssafy.soupapi.domain.project.mongodb.dao.MProjectRepository;
import io.ssafy.soupapi.domain.project.mongodb.entity.ChatMessage;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.util.DateConverterUtil;
import io.ssafy.soupapi.global.util.FindEntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final MProjectRepository mProjectRepository;
    private final RChatRepository rChatRepository;
    private final NotiService notiService;
    private final FindEntityUtil findEntityUtil;

    // 대화 저장
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
        for (String mentioneeId : chatMessageReq.mentionedMemberIds()) {
            RMentionNoti RMentionNoti = notiService.generateMentionNotiRedis(RChatMessage.chatMessageId(), chatMessageReq.senderId(), mentioneeId);

            // TODO: 2-1. 태그 알림 -> PostgreSQL 저장

            // 2-2. 태그 알림 -> Redis 저장
            notiService.saveMentionNotiToRedis(chatroomId, RMentionNoti, sentAtLong);
        }

        // 3. 채팅 메시지 -> Redis 저장
        rChatRepository.saveMessageToRedis(chatroomId, RChatMessage, sentAtLong);

        ZonedDateTime sentAtZdt = DateConverterUtil.instantToKstZdt(sentAtInstant);
//        log.info("sentAt -> [Instant] {} / [ZonedDateTime] {}", sentAtInstant, sentAtZdt);
        return generateChatMessageRes(RChatMessage.chatMessageId(), chatMessageReq, sentAtZdt);
    }

    public List<GetChatMessageRes> getChatMessages(String chatroomId, PageOffsetRequest pageOffsetRequest, LocalDateTime standardTime) {
        List<GetChatMessageRes> result = new ArrayList<>();
        List<RChatMessage> rChatMessageList;
        List<ChatMessage> mChatMessageList;
        Map<String, Member> senderMap = new HashMap<>();

        Long reqTime = DateConverterUtil.ldtToLong(standardTime);
        long offset = pageOffsetRequest.calculateOffset();
        rChatMessageList = rChatRepository.getNMessagesBefore(chatroomId, reqTime, offset, pageOffsetRequest.size());

        for (RChatMessage rChatMessage : rChatMessageList) {
            senderMap.put(rChatMessage.senderId(), null);
            result.add(rChatMessage.toGetChatMessageRes());
        }

        // MongoDB에, redis에서 발견한 earliest 메시지 이전에 발행된 메시지가 있는지 조회
        int rDataSize = rChatMessageList.size();
        if (rDataSize < pageOffsetRequest.size()) {
            int mDataSize = pageOffsetRequest.size() - rDataSize;

            Instant mLdt; // redis에서 earliest 메시지의 sentAt
            if (rDataSize == 0) { // redis에 (기준 시간 상관 없이) earlliest 메시지의 sentAt 조회해야
                rChatMessageList = rChatRepository.getMessageByIndex(chatroomId, 0, 1);
            }
            mLdt = rChatMessageList.get(0).sentAt();

            mChatMessageList = mProjectRepository.getNChatMessagesBefore(chatroomId, mLdt, mDataSize);

            for (ChatMessage mChatMessage : mChatMessageList) {
                senderMap.put(mChatMessage.getSenderId(), null);
                result.add(mChatMessage.toGetChatMessageRes());
            }
        }

        for (String memberId : senderMap.keySet()) {
            Member member = findEntityUtil.findMemberById(UUID.fromString(memberId));
            senderMap.put(memberId, member);
        }

        for (GetChatMessageRes res : result) {
            String senderId = res.getSender().getMemberId();
            res.getSender().setNickname(senderMap.get(senderId).getNickname());
            res.getSender().setProfileImageUrl(senderMap.get(senderId).getProfileImageUrl());
        }

        return result;
    }

    private ChatMessageRes generateChatMessageRes(String chatMessageId, ChatMessageReq chatMessageReq, ZonedDateTime sentAt) {
        return ChatMessageRes.builder()
                .chatMessageId(chatMessageId)
                .senderId(chatMessageReq.senderId())
                .message(chatMessageReq.message())
                .sentAt(sentAt.toString())
                .mentionedMemberIds(chatMessageReq.mentionedMemberIds())
                .build();
    }

}
