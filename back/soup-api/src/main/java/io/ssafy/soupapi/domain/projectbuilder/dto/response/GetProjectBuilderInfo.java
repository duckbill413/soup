package io.ssafy.soupapi.domain.projectbuilder.dto.response;

import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderInfo;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.SpringPackaging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "프로젝트 빌드 정보")
public record GetProjectBuilderInfo(
        @Schema(description = "springboot_type")
        String type,
        @Schema(description = "springboot_language")
        String language,
        @Schema(description = "springboot_language_version")
        String languageVersion,
        @Schema(description = "springboot_version")
        String version,
        @Schema(description = "springboot_packaging")
        SpringPackaging packaging,
        @Schema(description = "springboot_group")
        String group,
        @Schema(description = "springboot_artifact")
        String artifact,
        @Schema(description = "springboot_name")
        String name,
        @Schema(description = "springboot_description")
        String description,
        @Schema(description = "springboot_package_name")
        String packageName,
        @Schema(description = "springboot_dependencies")
        List<GetDependency> dependencies,
        @Schema(description = "built_project_s3_uri")
        String s3Url
) {
    public static GetProjectBuilderInfo of(ProjectBuilderInfo builderInfo) {
        return GetProjectBuilderInfo.builder()
                .type(builderInfo.getType())
                .language(builderInfo.getLanguage())
                .languageVersion(builderInfo.getLanguageVersion())
                .version(builderInfo.getVersion())
                .group(builderInfo.getGroup())
                .artifact(builderInfo.getArtifact())
                .packaging(builderInfo.getPackaging())
                .name(builderInfo.getName())
                .dependencies(builderInfo.getDependencies().stream().map(GetDependency::of).toList())
                .description(builderInfo.getDescription())
                .packageName(builderInfo.getPackageName())
                .s3Url(builderInfo.getS3Url())
                .build();
    }
}
