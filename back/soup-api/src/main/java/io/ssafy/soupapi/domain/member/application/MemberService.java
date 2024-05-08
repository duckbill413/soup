package io.ssafy.soupapi.domain.member.application;

import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;

import java.util.UUID;

public interface MemberService {

    Member findById(UUID memberId);

    String updateNickname(String nickname, UserSecurityDTO userSecurityDTO); // TODO: security member
}
