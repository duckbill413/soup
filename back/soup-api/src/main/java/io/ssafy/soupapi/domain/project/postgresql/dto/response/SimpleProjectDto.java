package io.ssafy.soupapi.domain.project.postgresql.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Simple 프로젝트 정보")
public record SimpleProjectDto(
        @Schema(description = "프로젝트 ID")
        String id,
        @Schema(description = "프로젝트 이름")
        String name,
        @Schema(description = "프로젝트 이미지 URL")
        String imgUrl
) {
}
