package io.ssafy.soupapi.auth;

import io.ssafy.soupapi.domain.member.dao.MemberRepository;
import io.ssafy.soupapi.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MemberRTests {
    @Autowired
    private MemberRepository memberRepository;
    @DisplayName("sofsfs")
    @Test
    void sofs() {
        // given
        List<Member> all = memberRepository.findAll();
        for (Member member : all) {
            System.out.println(member.getId());
        }

        // when

        // then

    }
}
