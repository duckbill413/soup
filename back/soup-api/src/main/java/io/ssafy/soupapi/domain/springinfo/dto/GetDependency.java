package io.ssafy.soupapi.domain.springinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "spring dependency 정보")
public record GetDependency(
        @Schema(description = "dependency 이름")
        String names,
        @Schema(description = "dependency 설명")
        String description,
        @Schema(description = "dependency 유형")
        String category
) {
}
