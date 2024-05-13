package io.ssafy.soupapi.domain.chat.dto;

import io.ssafy.soupapi.domain.chat.dto.response.GetChatMessageRes;
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

    public GetChatMessageRes toGetChatMessageRes() {
        GetChatMessageRes res = GetChatMessageRes.builder()
                .chatMessageId(chatMessageId).message(message).sentAt(DateConverterUtil.instantToKstZdt(sentAt))
                .build();
        res.setSenderId(senderId);
        return res;
    }

}
