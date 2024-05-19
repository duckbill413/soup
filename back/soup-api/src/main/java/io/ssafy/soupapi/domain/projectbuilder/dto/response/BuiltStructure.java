package io.ssafy.soupapi.domain.projectbuilder.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "빌드 완료된 프로젝트 정보")
public record BuiltStructure(
        @Schema(description = "빌드 시점의 프로젝트 빌드 정보")
        GetProjectBuilderInfo info,
        @Schema(description = "빌드된 프로젝트 구조")
        Map<String, Object> build
) {
}
