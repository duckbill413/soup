package io.ssafy.soupapi.domain.member.application;

import io.ssafy.soupapi.domain.member.dao.MemberRepository;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public String updateNickname(String nickname, UserSecurityDTO userSecurityDTO) {
        var m = memberRepository.findById(userSecurityDTO.getId()).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_USER));
        m.setNickname(nickname);
        memberRepository.save(m);
        return nickname + "으로 업데이트 완료";
    }
}
