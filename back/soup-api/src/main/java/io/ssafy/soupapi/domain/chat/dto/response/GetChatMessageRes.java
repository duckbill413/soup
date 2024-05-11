package io.ssafy.soupapi.domain.chat.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 채팅방에서 채팅 메시지 목록을 조회 시(API) FE에 전달해주는 채팅 메시지
@Builder @Getter @Setter
public class GetChatMessageRes {
    private String chatMessageId;
    private String message;
    private LocalDateTime sentAt;
    @Builder.Default
    private Member sender = new Member();

    @Getter @Setter
    public static class Member {
        private String memberId;
        private String nickname;
        private String profileImageUrl;
    }

    public void setSenderId(String memberId) {
        sender.memberId = memberId;
    }

}
