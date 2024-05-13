package io.ssafy.soupapi.domain.projectbuilder.dto.request;

import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderDependency;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderInfo;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.SpringPackaging;
import io.ssafy.soupapi.domain.springinfo.entity.Dependency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "프로젝트 빌드 정보 변경")
public record ChangeProjectBuilderInfo(
        @NotNull(message = "type 값이 null일 수 없습니다.")
        @NotEmpty(message = "type 값이 공백 입니다.")
        @Schema(description = "springboot_type", defaultValue = "Gradle-Groovy")
        String type,
        @NotNull(message = "language 값이 null일 수 없습니다.")
        @NotEmpty(message = "language 값이 공백 입니다.")
        @Schema(description = "springboot_language", defaultValue = "Java")
        String language,
        @NotNull(message = "language version 값이 null일 수 없습니다.")
        @NotEmpty(message = "language version 값이 공백 입니다.")
        @Schema(description = "springboot_language_version", defaultValue = "17")
        String languageVersion,
        @NotNull(message = "version 값이 null일 수 없습니다.")
        @NotEmpty(message = "version 값이 공백 입니다.")
        @Schema(description = "springboot_version", defaultValue = "3.2.5")
        String version,
        @NotNull(message = "packaging 정보를 확인해 주세요.")
        @Schema(description = "spring boot packaging 정보", defaultValue = "Jar")
        SpringPackaging packaging,
        @NotNull(message = "group 값이 null일 수 없습니다.")
        @NotEmpty(message = "group 값이 공백 입니다.")
        @Schema(description = "springboot_group", defaultValue = "com.example")
        String group,
        @NotNull(message = "artifact 값이 null일 수 없습니다.")
        @NotEmpty(message = "artifact 값이 공백 입니다.")
        @Schema(description = "springboot_artifact", defaultValue = "demo")
        String artifact,
        @NotNull(message = "name 값이 null일 수 없습니다.")
        @NotEmpty(message = "name 값이 공백 입니다.")
        @Schema(description = "springboot_name", defaultValue = "demo")
        String name,
        @NotNull(message = "description 값이 null일 수 없습니다.")
        @NotEmpty(message = "description 값이 공백 입니다.")
        @Schema(description = "springboot_description", defaultValue = "demo project with soup!")
        String description,
        @NotNull(message = "package name 값이 null일 수 없습니다.")
        @NotEmpty(message = "package name 값이 공백 입니다.")
        @Schema(description = "springboot_package_name", defaultValue = "com.example.demo")
        String packageName,
        @Schema(description = "springboot_dependency_ids")
        List<Long> dependencies
) {
    public static ProjectBuilderInfo to(ChangeProjectBuilderInfo builderInfo, List<Dependency> dependencies) {
        return ProjectBuilderInfo.builder()
                .type(builderInfo.type())
                .language(builderInfo.language())
                .languageVersion(builderInfo.languageVersion())
                .version(builderInfo.version())
                .group(builderInfo.group())
                .artifact(builderInfo.artifact())
                .packaging(builderInfo.packaging())
                .name(builderInfo.name())
                .dependencies(dependencies.stream().map(d -> ProjectBuilderDependency.builder()
                        .id(d.getId())
                        .name(d.getName())
                        .description(d.getDescription())
                        .code(d.getCode())
                        .category(d.getCategory())
                        .basic(d.isBasic())
                        .build()).toList()
                )
                .description(builderInfo.description())
                .packageName(builderInfo.packageName())
                .build();
    }
}
