package io.ssafy.soupapi.domain.project.mongodb.dto.request;

import io.ssafy.soupapi.domain.project.mongodb.entity.Tool;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
@Schema(description = "프로젝트 툴 업데이트")
public record UpdateProjectTool(
        @NotEmpty(message = "tool_name을 확인해 주세요.")
        @Schema(description = "tool_name")
        String toolName,
        @NotEmpty(message = "tool_url을 확인해 주세요.")
        @Schema(description = "tool_url")
        String toolUrl
) {
    public static Tool toTool(UpdateProjectTool updateProjectTool) {
        return Tool.builder()
                .toolName(updateProjectTool.toolName())
                .url(updateProjectTool.toolUrl())
                .build();
    }
}
