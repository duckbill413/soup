package io.ssafy.soupapi.domain.member.dao;

import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.member.entity.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    List<Member> findByEmail(String email);

    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
