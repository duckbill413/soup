package io.ssafy.soupapi.domain.chat.entity;

import io.ssafy.soupapi.domain.chat.dto.response.ChatMessageRes;
import io.ssafy.soupapi.global.util.DateConverterUtil;
import lombok.Builder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// redis에 저장하는 채팅 메시지
public record RChatMessage(
        String chatMessageId,
        String senderId,
        String message,
        Instant sentAt,
        List<String> mentioneeIds
) {

    @Builder
    public RChatMessage {
        if (Objects.isNull(mentioneeIds)) mentioneeIds = new ArrayList<>();
    }

    public ChatMessageRes toChatMessageRes() {
        ChatMessageRes res = ChatMessageRes.builder()
                .chatMessageId(chatMessageId)
                .message(message)
                .sentAt(DateConverterUtil.instantToKstZdt(sentAt))
                .mentionedMemberIds(mentioneeIds)
                .build();
        res.getSender().setMemberId(senderId);
        return res;
    }

}
