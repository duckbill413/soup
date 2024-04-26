package io.ssafy.soupapi.domain.member.application;

import io.ssafy.soupapi.global.security.TemporalMember;

public interface MemberService {
    String updateNickname(String nickname, TemporalMember member); // TODO: security member
}
