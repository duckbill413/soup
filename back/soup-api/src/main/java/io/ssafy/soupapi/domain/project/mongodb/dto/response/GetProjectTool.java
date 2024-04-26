package io.ssafy.soupapi.domain.project.mongodb.dto.response;

import io.ssafy.soupapi.domain.project.mongodb.entity.Tool;
import io.ssafy.soupapi.global.util.StringParserUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프로젝트 관리툴")
public record GetProjectTool(
        @Schema(description = "tool_name")
        String toolName,
        @Schema(description = "tool_url")
        String toolUrl
) {
    @Builder
    public GetProjectTool {
        toolName = StringParserUtil.parseNullToEmpty(toolName);
        toolUrl = StringParserUtil.parseNullToEmpty(toolUrl);
    }

    public static GetProjectTool toProjectToolDto(Tool tool) {
        return GetProjectTool.builder()
                .toolName(tool.getToolName())
                .toolUrl(tool.getUrl())
                .build();
    }
}
