package io.ssafy.soupapi.domain.projectbuilder.dto.liveblock;

import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderDependency;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderInfo;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.SpringPackaging;
import io.ssafy.soupapi.domain.springinfo.entity.Dependency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;
import java.util.Objects;

public record LiveChangeProjectBuilderInfo(
        @NotNull(message = "project 값이 null일 수 없습니다.")
        @NotEmpty(message = "project 값이 공백 입니다.")
        String project,
        @NotNull(message = "language 값이 null일 수 없습니다.")
        @NotEmpty(message = "language 값이 공백 입니다.")
        String language,
        @NotNull(message = "version 값이 null일 수 없습니다.")
        @NotEmpty(message = "version 값이 공백 입니다.")
        String springVersion,
        @NotNull(message = "packaging 정보를 확인해 주세요.")
        @Schema(description = "spring boot packaging 정보", defaultValue = "Jar")
        String packaging,
        @NotNull(message = "language version 값이 null일 수 없습니다.")
        @NotEmpty(message = "language version 값이 공백 입니다.")
        @Schema(description = "springboot_language_version", defaultValue = "17")
        String javaVersion,
        List<Long> dependencies,
        LiveMetaData metadata
) {
    @Builder
    public LiveChangeProjectBuilderInfo {
        if (Objects.isNull(dependencies)) {
            dependencies = List.of();
        }
    }

    public static ProjectBuilderInfo toProjectBuilderInfo(LiveChangeProjectBuilderInfo builderInfo, List<Dependency> dependencies) {
        return ProjectBuilderInfo.builder()
                .type(builderInfo.project())
                .language(builderInfo.language())
                .languageVersion(builderInfo.javaVersion())
                .version(builderInfo.springVersion())
                .packaging(SpringPackaging.valueOf(builderInfo.packaging()))
                .group(builderInfo.metadata().group())
                .artifact(builderInfo.metadata().artifact())
                .name(builderInfo.metadata().name())
                .description(builderInfo.metadata().description())
                .packageName(builderInfo.metadata().packageName())
                .dependencies(dependencies.stream().map(d -> ProjectBuilderDependency.builder()
                        .id(d.getId())
                        .name(d.getName())
                        .description(d.getDescription())
                        .code(d.getCode())
                        .category(d.getCategory())
                        .basic(d.isBasic())
                        .build()).toList()
                )
                .build();
    }
}
