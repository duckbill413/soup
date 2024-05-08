package io.ssafy.soupapi.domain.member.application;

import io.ssafy.soupapi.domain.member.dto.response.GetLiveblocksTokenRes;

import java.util.UUID;

public interface MemberUsecase {

    GetLiveblocksTokenRes getLiveblocksUserIdToken(UUID memberId);

}
