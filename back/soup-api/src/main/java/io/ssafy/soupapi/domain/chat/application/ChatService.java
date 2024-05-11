package io.ssafy.soupapi.domain.chat.application;

import io.ssafy.soupapi.domain.chat.dao.RChatRepository;
import io.ssafy.soupapi.domain.chat.dto.RChatMessage;
import io.ssafy.soupapi.domain.chat.dto.request.ChatMessageReq;
import io.ssafy.soupapi.domain.chat.dto.response.ChatMessageRes;
import io.ssafy.soupapi.domain.chat.dto.response.GetChatMessageRes;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.noti.application.NotiService;
import io.ssafy.soupapi.domain.noti.dto.MentionNotiRedis;
import io.ssafy.soupapi.domain.project.mongodb.dao.MProjectRepository;
import io.ssafy.soupapi.domain.project.mongodb.entity.ChatMessage;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.util.FindEntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        LocalDateTime sentAtLdt = Instant.ofEpochMilli(sentAtLong).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        String chatMessageId = UUID.randomUUID().toString();
        RChatMessage RChatMessage = chatMessageReq.toChatMessageRedis(chatMessageId, sentAtLdt);
        ChatMessage chatMessage = chatMessageReq.toMChatMessage(chatMessageId, sentAtLdt);

        // 1. 채팅 메시지 -> MongoDB 저장
        mProjectRepository.addChatMessage(new ObjectId(chatroomId), chatMessage);

        // 2. 태그 알림
        for (String mentioneeId : chatMessageReq.mentionedMemberIds()) {
            MentionNotiRedis mentionNotiRedis = notiService.generateMentionNotiRedis(RChatMessage.chatMessageId(), chatMessageReq.senderId(), mentioneeId);

            // TODO: 2-1. PostgreSQL 저장

            // 2-2. Redis 저장
            notiService.saveMentionNotiToRedis(chatroomId, mentionNotiRedis, sentAtLong);
        }

        // 3. 채팅 메시지 -> Redis 저장
        rChatRepository.saveMessageToRedis(chatroomId, RChatMessage, sentAtLong);

        return generateChatMessageRes(RChatMessage.chatMessageId(), chatMessageReq, sentAtLdt.toString());
    }

    public List<GetChatMessageRes> getChatMessages(String chatroomId, PageOffsetRequest pageOffsetRequest) {
        List<GetChatMessageRes> result = new ArrayList<>();
        List<RChatMessage> rChatMessageList;
        List<ChatMessage> mChatMessageList;
        Map<String, Member> senderMap = new HashMap<>();

        int startIndex = (pageOffsetRequest.page() - 1) * pageOffsetRequest.size();
        int endIndex = startIndex + pageOffsetRequest.size() - 1;
        rChatMessageList = rChatRepository.getMessage(chatroomId, pageOffsetRequest.size(), startIndex, endIndex);

        for (RChatMessage rChatMessage : rChatMessageList) {
            senderMap.put(rChatMessage.senderId(), null);
            result.add(rChatMessage.toGetChatMessageRes());
        }

        // MongoDB에, redis에서 발견한 마지막 메시지의 sentAt 이후에 발행된 메시지가 있는 지 조회
        if (rChatMessageList.size() < pageOffsetRequest.size()) {
            int rDataSize = rChatMessageList.size();
            int mDataSize = pageOffsetRequest.size() - rDataSize;
            LocalDateTime mLdt = rChatMessageList.get(rDataSize - 1).sentAt();
//            Date mDate = DateConverterUtil.ldtToDate(mLdt, "Z");

            mChatMessageList = mProjectRepository.getNChatMessagesAfter(chatroomId, mLdt, mDataSize);

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

    private ChatMessageRes generateChatMessageRes(String chatMessageId, ChatMessageReq chatMessageReq, String sentAt) {
        return ChatMessageRes.builder()
                .chatMessageId(chatMessageId)
                .senderId(chatMessageReq.senderId())
                .message(chatMessageReq.message())
                .sentAt(sentAt)
                .mentionedMemberIds(chatMessageReq.mentionedMemberIds())
                .build();
    }

}
