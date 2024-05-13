package io.ssafy.soupapi.domain.project.mongodb.entity;

import io.ssafy.soupapi.domain.chat.dto.response.GetChatMessageRes;
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

    public GetChatMessageRes toGetChatMessageRes() {
        GetChatMessageRes res = GetChatMessageRes.builder()
                .chatMessageId(id).message(content).sentAt(DateConverterUtil.instantToKstZdt(timestamp))
                .build();
        res.setSenderId(senderId);
        return res;
    }

}
