package io.ssafy.soupapi.domain.projectbuilder.dto.response;

import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderDependency;
import lombok.Builder;

@Builder
public record GetDependency(
        Long id,
        String name,
        String description,
        String category,
        String code,
        boolean basic
) {
    public static GetDependency of(ProjectBuilderDependency dependency) {
        return GetDependency.builder()
                .id(dependency.getId())
                .name(dependency.getName())
                .description(dependency.getDescription())
                .category(dependency.getCategory())
                .code(dependency.getCode())
                .basic(dependency.isBasic())
                .build();
    }
}
