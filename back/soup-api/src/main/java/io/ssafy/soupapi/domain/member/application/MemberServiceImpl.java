package io.ssafy.soupapi.domain.member.application;

import io.ssafy.soupapi.domain.member.dao.MemberRepository;
import io.ssafy.soupapi.domain.member.dto.response.GetLiveblocksTokenRes;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.global.external.liveblocks.dao.LbFeignClient;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.GetUserIdTokenReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.response.GetUserIdTokenRes;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.ssafy.soupapi.global.util.FindEntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final FindEntityUtil findEntityUtil;
    private final MemberRepository memberRepository;
    private final LbFeignClient lbFeignClient;

    @Transactional
    @Override
    public String updateNickname(String nickname, UserSecurityDTO userSecurityDTO) {
        var m = findEntityUtil.findMemberById(userSecurityDTO.getId());
        m.setNickname(nickname);
        memberRepository.save(m);
        return nickname + "으로 업데이트 완료";
    }

    // Liveblocks에 유저(id: userId)를 등록하고 ID token을 얻는다
    @Override
    public GetLiveblocksTokenRes getLiveblocksUserIdToken(UUID memberId) {
        Member member = findEntityUtil.findMemberById(memberId);
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
                = lbFeignClient.getLiveblocksUserIdToken(getUserIdTokenReq);
        return GetLiveblocksTokenRes.builder()
                .liveblocksIdToken(getUserIdTokenRes.token())
                .build();
    }
}
