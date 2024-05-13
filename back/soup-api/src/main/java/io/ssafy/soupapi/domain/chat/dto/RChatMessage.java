package io.ssafy.soupapi.domain.chat.dto;

import io.ssafy.soupapi.domain.chat.dto.response.ChatMessageRes;
import io.ssafy.soupapi.global.util.DateConverterUtil;
import lombok.Builder;

import java.time.Instant;

// redis에 저장하는 채팅 메시지
@Builder
public record RChatMessage(
        String chatMessageId,
        String senderId,
        String message,
        Instant sentAt
) {

    public ChatMessageRes toChatMessageRes() {
        ChatMessageRes res = ChatMessageRes.builder()
                .chatMessageId(chatMessageId).message(message).sentAt(DateConverterUtil.instantToKstZdt(sentAt))
                .build();
        res.getSender().setMemberId(senderId);
        return res;
    }

}
