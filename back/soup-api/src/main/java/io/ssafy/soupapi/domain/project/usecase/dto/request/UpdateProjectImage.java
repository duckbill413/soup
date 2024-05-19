package io.ssafy.soupapi.domain.project.usecase.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프로젝트 이미지 업데이트")
public record UpdateProjectImage(
        @Schema(description = "프로젝트 이미지 링크")
        String imgUrl
) {
}
