package io.ssafy.soupapi.domain.projectbuilder.dto.liveblock;

import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderDependency;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderInfo;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.SpringPackaging;
import io.ssafy.soupapi.domain.springinfo.entity.Dependency;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public record LiveProjectBuilderInfo(
        @NotNull(message = "type 값이 null일 수 없습니다.")
        @NotEmpty(message = "type 값이 공백 입니다.")
        String type,
        @NotNull(message = "language 값이 null일 수 없습니다.")
        @NotEmpty(message = "language 값이 공백 입니다.")
        String language,
        @NotNull(message = "version 값이 null일 수 없습니다.")
        @NotEmpty(message = "version 값이 공백 입니다.")
        String version,
        @NotNull(message = "packaging 정보를 확인해 주세요.")
        @Schema(description = "spring boot packaging 정보", defaultValue = "Jar")
        String packaging,
        @NotNull(message = "language version 값이 null일 수 없습니다.")
        @NotEmpty(message = "language version 값이 공백 입니다.")
        @Schema(description = "springboot_language_version", defaultValue = "17")
        String languageVersion,
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
        @NotNull(message = "language version 값이 null일 수 없습니다.")
        @NotEmpty(message = "language version 값이 공백 입니다.")
        String packageName,
        List<Long> dependencies
) {
    @Builder
    public LiveProjectBuilderInfo {
        if (Objects.isNull(dependencies)) {
            dependencies = List.of();
        }
    }

    public static ProjectBuilderInfo toProjectBuilderInfo(LiveProjectBuilderInfo builderInfo, List<Dependency> dependencies) {
        isValid(builderInfo);
        return ProjectBuilderInfo.builder()
                .type(builderInfo.type())
                .language(builderInfo.language())
                .languageVersion(builderInfo.languageVersion())
                .version(builderInfo.version())
                .packaging(SpringPackaging.valueOf(builderInfo.packaging()))
                .group(builderInfo.group())
                .artifact(builderInfo.artifact())
                .name(builderInfo.name())
                .description(builderInfo.description())
                .packageName(builderInfo.packageName())
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

    private static boolean isValid(LiveProjectBuilderInfo builderInfo) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<LiveProjectBuilderInfo>> violations = validator.validate(builderInfo);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<LiveProjectBuilderInfo> violation : violations) {
                sb.append(violation.getMessage()).append(", ");
            }
            throw new BaseExceptionHandler(ErrorCode.UNABLE_TO_USE_THIS_BUILDER_INFO, "유효성 검사 실패: " + sb);
        }

        return true;
    }
}
