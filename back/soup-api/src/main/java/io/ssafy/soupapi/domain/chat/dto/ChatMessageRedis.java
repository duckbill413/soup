package io.ssafy.soupapi.domain.chat.dto;

import io.ssafy.soupapi.domain.chat.dto.request.ChatMessageDto;
import lombok.Builder;

// 채팅 메시지 관련 redis에 저장하는 정보
@Builder
public record ChatMessageRedis(
        String chatMessageId,
        String senderId,
        String message,
        String createdAt
) {
}
