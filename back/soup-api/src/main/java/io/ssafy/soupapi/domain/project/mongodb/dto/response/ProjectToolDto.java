package io.ssafy.soupapi.domain.project.mongodb.dto.response;

import io.ssafy.soupapi.domain.project.mongodb.entity.Tool;
import io.ssafy.soupapi.global.util.StringParserUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프로젝트 관리툴")
public record ProjectToolDto(
        @Schema(description = "tool_name")
        String toolName,
        @Schema(description = "tool_url")
        String toolUrl
) {
    @Builder
    public ProjectToolDto {
        toolName = StringParserUtil.parseNullToEmpty(toolName);
        toolUrl = StringParserUtil.parseNullToEmpty(toolUrl);
    }

    public static ProjectToolDto toProjectToolDto(Tool tool) {
        return ProjectToolDto.builder()
                .toolName(tool.getToolName())
                .toolUrl(tool.getUrl())
                .build();
    }
}
