package io.ssafy.soupapi.domain.chat.dto.request;

public record ChatMessageDto(
    String sender,
    String message,
    String createdAt,
    String mentionedMember // 누군가를 태그했을 때 태그 당한 memberId
) {
}
