package io.ssafy.soupapi.domain.project.mongodb.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "프로젝트 지라 키 정보 업데이트")
public record UpdateProjectJiraKey(
        @NotEmpty(message = "유저명을 확인해 주세요.")
        @Schema(description = "Project Jira Username")
        String username,
        @NotEmpty(message = "Jira key를 확인해 주세요.")
        @Schema(description = "Project Jira key")
        String key
) {
}
