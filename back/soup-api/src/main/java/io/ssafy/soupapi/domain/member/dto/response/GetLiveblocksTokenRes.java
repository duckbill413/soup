package io.ssafy.soupapi.domain.member.dto.response;

import lombok.Builder;

@Builder
public record GetLiveblocksTokenRes(
    String liveblocksIdToken
) {
}
