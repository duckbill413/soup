package io.ssafy.soupapi.domain.project.usecase.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "프로젝트 생성 요청")
public record CreateProjectDto(
        @NotEmpty(message = "프로젝트 이름을 지정해 주세요.")
        @Schema(description = "프로젝트 이름")
        String name,
        @Schema(description = "프로젝트 이미지")
        String imgUrl
) {
}
