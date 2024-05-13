package io.ssafy.soupapi.domain.chat.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChatSender {
    private String memberId;
    private String nickname;
    private String profileImageUrl;
}
