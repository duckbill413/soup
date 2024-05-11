package io.ssafy.soupapi.domain.chat.dto.request;

import io.ssafy.soupapi.domain.chat.dto.RChatMessage;
import io.ssafy.soupapi.domain.project.mongodb.entity.ChatMessage;

import java.time.LocalDateTime;
import java.util.List;

// 웹소켓 stomp 로 채팅을 할 때 publisher가 보내오는 메시지
public record ChatMessageReq(
        String senderId,
        String message,
        List<String> mentionedMemberIds // 누군가를 태그했을 때 태그 당한 memberId
) {

    public RChatMessage toChatMessageRedis(String chatMessageId, LocalDateTime sentAt) {
        return RChatMessage.builder()
                .chatMessageId(chatMessageId)
                .senderId(senderId).message(message).sentAt(sentAt)
                .build();
    }

    public ChatMessage toMChatMessage(String chatMessageId, LocalDateTime sentAt) {
        return ChatMessage.builder()
                .id(chatMessageId)
                .senderId(senderId).content(message).timestamp(sentAt.toString())
                .build();
    }

}
