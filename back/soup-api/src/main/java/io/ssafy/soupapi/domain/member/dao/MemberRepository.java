package io.ssafy.soupapi.domain.member.dao;

import io.ssafy.soupapi.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
}
