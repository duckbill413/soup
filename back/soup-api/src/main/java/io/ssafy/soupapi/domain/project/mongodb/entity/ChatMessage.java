package io.ssafy.soupapi.domain.project.mongodb.entity;

import io.ssafy.soupapi.domain.chat.dto.RChatMessage;
import io.ssafy.soupapi.domain.chat.dto.response.ChatMessageRes;
import io.ssafy.soupapi.global.util.DateConverterUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Slf4j
@Getter
@Setter
@Builder
public class ChatMessage {
    @Field("chat_message_id")
    private String id;
    @Field("chat_message_sender_id")
    private String senderId;
    @Field("chat_message_content")
    private String content;
    @Field("chat_message_timestamp")
    private Instant timestamp;

    public ChatMessageRes toGetChatMessageRes() {
        ChatMessageRes res = ChatMessageRes.builder()
                .chatMessageId(id).message(content).sentAt(DateConverterUtil.instantToKstZdt(timestamp))
                .build();
        res.getSender().setMemberId(senderId);
        return res;
    }

    public RChatMessage toRChatMessage() {
        return RChatMessage.builder()
                .chatMessageId(id)
                .senderId(senderId)
                .message(content)
                .sentAt(timestamp)
                .build();
    }

}
