package io.ssafy.soupapi.domain.springinfo.dto;

import io.ssafy.soupapi.domain.springinfo.entity.Dependency;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "spring dependency 정보")
public record GetDependency(
        @Schema(description = "dependency id")
        Long id,
        @Schema(description = "dependency 이름")
        String name,
        @Schema(description = "dependency 설명")
        String description,
        @Schema(description = "dependency 유형")
        String category,
        @Schema(description = "기본 dependency")
        boolean basic
) {
    public static GetDependency of(Dependency dependency) {
        return GetDependency.builder()
                .id(dependency.getId())
                .name(dependency.getName())
                .description(dependency.getDescription())
                .category(dependency.getCategory())
                .basic(dependency.isBasic())
                .build();
    }
}
