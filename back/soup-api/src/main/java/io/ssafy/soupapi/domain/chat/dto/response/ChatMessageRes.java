package io.ssafy.soupapi.domain.chat.dto.response;

import io.ssafy.soupapi.domain.chat.dto.ChatSender;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// 웹소켓 stomp 로 채팅을 할 때 subscriber에 전달해주는 메시지 / 채팅 로그 조회 API 사용 시 FE에 전달해주는 메시지
@Setter @Getter
@RequiredArgsConstructor
public class ChatMessageRes {
    private String chatMessageId;
    private ChatSender sender = new ChatSender();
    private String message;
    private ZonedDateTime sentAt;
    private List<String> mentionedMemberIds = new ArrayList<>();// 누군가를 태그했을 때 태그 당한 memberId

    @Builder
    public ChatMessageRes(String chatMessageId, ChatSender sender, String message, ZonedDateTime sentAt, List<String> mentionedMemberIds) {
        this.chatMessageId = chatMessageId;
        if (Objects.isNull(sender)) this.sender = new ChatSender();
        else this.sender = sender;
        this.message = message;
        this.sentAt = sentAt;
        this.mentionedMemberIds = mentionedMemberIds;
    }
}
