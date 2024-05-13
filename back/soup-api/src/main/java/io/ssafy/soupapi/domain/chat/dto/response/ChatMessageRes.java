package io.ssafy.soupapi.domain.chat.dto.response;

import lombok.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

// 웹소켓 stomp 로 채팅을 할 때 subscriber에 전달해주는 메시지
@Setter @Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ChatMessageRes {
    private String chatMessageId;
    private String senderId;
    private String message;
    private ZonedDateTime sentAt;
    private List<String> mentionedMemberIds = new ArrayList<>();// 누군가를 태그했을 때 태그 당한 memberId
}
