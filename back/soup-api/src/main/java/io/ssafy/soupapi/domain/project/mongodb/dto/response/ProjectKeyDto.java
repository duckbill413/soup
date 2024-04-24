package io.ssafy.soupapi.domain.project.mongodb.dto.response;

import io.ssafy.soupapi.domain.project.mongodb.entity.Info;
import io.ssafy.soupapi.global.util.StringParserUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프로젝트 키 정보")
public record ProjectKeyDto(
        @Schema(description = "프로젝트 jira username")
        String jiraUsername,
        @Schema(description = "프로젝트 jira key")
        String jiraKey
) {
    @Builder
    public ProjectKeyDto {
        jiraUsername = StringParserUtil.parseNullToEmpty(jiraUsername);
        jiraKey = StringParserUtil.parseNullToEmpty(jiraKey);
    }

    public static ProjectKeyDto toProjectInfoDto(Info info) {
        return ProjectKeyDto.builder()
                .jiraUsername(info.getJiraUsername())
                .jiraKey(info.getJiraKey())
                .build();
    }
}
