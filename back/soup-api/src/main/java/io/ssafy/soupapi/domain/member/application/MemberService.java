package io.ssafy.soupapi.domain.member.application;

import io.ssafy.soupapi.global.security.user.UserSecurityDTO;

public interface MemberService {
    String updateNickname(String nickname, UserSecurityDTO userSecurityDTO); // TODO: security member
}
