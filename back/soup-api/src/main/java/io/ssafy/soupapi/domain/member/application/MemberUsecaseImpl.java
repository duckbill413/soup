package io.ssafy.soupapi.domain.member.application;

import io.ssafy.soupapi.domain.member.dto.response.GetLiveblocksTokenRes;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.global.external.liveblocks.LiveblocksFeignClient;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.GetUserIdTokenReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.response.GetUserIdTokenRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberUsecaseImpl implements MemberUsecase {

    private final MemberService memberService;
    private final LiveblocksFeignClient liveblocksFeignClient;

    @Override
    public GetLiveblocksTokenRes getLiveblocksUserIdToken(UUID memberId) {
        Member member = memberService.findById(memberId);
        GetUserIdTokenReq getUserIdTokenReq = GetUserIdTokenReq.builder()
                .userId(memberId.toString())
                .groupIds(Collections.emptyList())
                .userInfo(
                        GetUserIdTokenReq.UserInfo.builder()
                                .name(member.getNickname())
                                .email(member.getEmail())
                                .profileImgUrl(member.getProfileImageUrl())
                                .build()
                )
                .build();
        GetUserIdTokenRes getUserIdTokenRes
                = liveblocksFeignClient.getLiveblocksUserIdToken(getUserIdTokenReq);
        return GetLiveblocksTokenRes.builder()
                .liveblocksIdToken(getUserIdTokenRes.token())
                .build();
    }
}
