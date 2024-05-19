package io.ssafy.soupapi.domain.project.mongodb.dto.response;

import io.ssafy.soupapi.domain.project.mongodb.entity.Tool;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "프로젝트 관리툴")
public record GetProjectTool(
        @Schema(description = "tool_name")
        String toolName,
        @Schema(description = "tool_url")
        String toolUrl
) {
    public static GetProjectTool toProjectToolDto(Tool tool) {
        return GetProjectTool.builder()
                .toolName(tool.getToolName())
                .toolUrl(tool.getUrl())
                .build();
    }
}
