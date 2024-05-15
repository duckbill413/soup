package io.ssafy.soupapi.domain.projectbuilder.dto.liveblock;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LiveMetaData(
        @NotNull(message = "group 값이 null일 수 없습니다.")
        @NotEmpty(message = "group 값이 공백 입니다.")
        String group,
        @NotNull(message = "artifact 값이 null일 수 없습니다.")
        @NotEmpty(message = "artifact 값이 공백 입니다.")
        String artifact,
        @NotNull(message = "name 값이 null일 수 없습니다.")
        @NotEmpty(message = "name 값이 공백 입니다.")
        String name,
        @NotNull(message = "description 값이 null일 수 없습니다.")
        @NotEmpty(message = "description 값이 공백 입니다.")
        String description,
        @NotNull(message = "package name 값이 null일 수 없습니다.")
        @NotEmpty(message = "package name 값이 공백 입니다.")
        String packageName
) {
}
