package io.ssafy.soupapi.domain.chat.dto.response;

import lombok.Builder;

import java.util.List;

// 웹소켓 stomp 로 채팅을 할 때 subscriber에 전달해주는 메시지
@Builder
public record ChatMessageRes(
        String chatMessageId,
        String senderId,
        String message,
        String sentAt,
        List<String> mentionedMemberIds // 누군가를 태그했을 때 태그 당한 memberId
) {
}
