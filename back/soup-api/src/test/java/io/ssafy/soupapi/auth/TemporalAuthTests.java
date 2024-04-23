package io.ssafy.soupapi.auth;

import io.ssafy.soupapi.domain.member.Member;
import io.ssafy.soupapi.domain.member.SocialType;
import io.ssafy.soupapi.domain.member.dao.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@SpringBootTest
public class TemporalAuthTests {
    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    @Rollback(value = false)
    @Test
    void createTemporalMember() {
        var member = Member.builder()
                .email("soup@soup.com")
                .type(SocialType.KAKAO)
                .build();
        var savedMember = memberRepository.save(member);
        log.info(savedMember.toString());
    }
}
