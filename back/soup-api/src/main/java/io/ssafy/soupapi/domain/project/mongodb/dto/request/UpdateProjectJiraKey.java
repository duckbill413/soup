package io.ssafy.soupapi.domain.project.mongodb.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "프로젝트 지라 키 정보 업데이트")
public record UpdateProjectJiraKey(
        @NotEmpty(message = "지라 호스트 정보를 확인해 주세요 (ex: ssafy)")
        @Schema(description = "jira host name", example = "ssafy")
        String host,
        @NotEmpty(message = "지라에 등록된 프로젝트 이름을 적어주세요")
        @Schema(description = "jira project key name")
        String projectKey,
        @NotEmpty(message = "유저명을 확인해 주세요.")
        @Schema(description = "Project Jira Username")
        String username,
        @NotEmpty(message = "Jira key를 확인해 주세요.")
        @Schema(description = "Project Jira key")
        String key
) {
}
