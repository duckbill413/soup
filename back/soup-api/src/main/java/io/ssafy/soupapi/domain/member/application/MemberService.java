package io.ssafy.soupapi.domain.member.application;

import io.ssafy.soupapi.domain.member.dto.response.GetLiveblocksTokenRes;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;

import java.util.UUID;

public interface MemberService {

    GetLiveblocksTokenRes getLiveblocksUserIdToken(UUID memberId);

    String updateNickname(String nickname, UserSecurityDTO userSecurityDTO);
}
