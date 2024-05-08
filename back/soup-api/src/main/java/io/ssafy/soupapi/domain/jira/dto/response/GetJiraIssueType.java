package io.ssafy.soupapi.domain.jira.dto.response;

import io.ssafy.soupapi.domain.jira.dto.Issuetype;
import lombok.Builder;

@Builder
public record GetJiraIssueType(
        String id,
        String name,
        String description
) {
    public static GetJiraIssueType of(Issuetype issuetype) {
        return GetJiraIssueType.builder()
                .id(issuetype.id)
                .name(issuetype.name)
                .description(issuetype.description)
                .build();
    }
}
