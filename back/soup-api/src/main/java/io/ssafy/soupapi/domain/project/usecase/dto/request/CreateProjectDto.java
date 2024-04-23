package io.ssafy.soupapi.domain.project.usecase.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateProjectDto(
        @NotEmpty(message = "프로젝트 이름을 지정해 주세요.")
        String name,
        String imgUrl
) {
}
