package io.ssafy.soupapi.domain.chat.dto;

import io.ssafy.soupapi.domain.chat.dto.response.GetChatMessageRes;
import lombok.Builder;

import java.time.LocalDateTime;

// redis에 저장하는 채팅 메시지
@Builder
public record RChatMessage(
        String chatMessageId,
        String senderId,
        String message,
        LocalDateTime sentAt
) {

    public GetChatMessageRes toGetChatMessageRes() {
        GetChatMessageRes res = GetChatMessageRes.builder()
                .chatMessageId(chatMessageId).message(message).sentAt(sentAt)
                .build();
        res.setSenderId(senderId);
        return res;
    }

}
