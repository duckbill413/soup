package io.ssafy.soupapi.domain.chat.dto.request;

import io.ssafy.soupapi.domain.chat.dto.ChatMessageRedis;

import java.util.List;
import java.util.UUID;

// 유저간 채팅 시 FE <-> BE 전달
public record ChatMessageDto(
        String senderId,
        String message,
        String createdAt,
        List<String> mentionedMemberIds // 누군가를 태그했을 때 태그 당한 memberId
) {

    public ChatMessageRedis toChatMessageRedis() {
        return ChatMessageRedis.builder()
                .chatMessageId(UUID.randomUUID().toString())
                .senderId(senderId).message(message).createdAt(createdAt)
                .build();
    }

}
