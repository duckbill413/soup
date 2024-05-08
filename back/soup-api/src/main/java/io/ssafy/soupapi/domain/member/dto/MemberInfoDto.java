package io.ssafy.soupapi.domain.member.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MemberInfoDto(
        UUID memberId,
        String email,
        String nickname,
        String phone,
        String profileImageUrl
) {
}
