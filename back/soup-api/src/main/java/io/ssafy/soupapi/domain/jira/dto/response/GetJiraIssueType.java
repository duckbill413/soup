package io.ssafy.soupapi.domain.jira.dto.response;

import io.ssafy.soupapi.domain.jira.dto.Issuetype;
import io.ssafy.soupapi.global.util.StringParserUtil;
import lombok.Builder;

public record GetJiraIssueType(
        String id,
        String name,
        String description
) {
    @Builder
    public GetJiraIssueType {
        id = StringParserUtil.parseNullToEmpty(id);
        name = StringParserUtil.parseNullToEmpty(name);
        description = StringParserUtil.parseNullToEmpty(description);
    }

    public static GetJiraIssueType of(Issuetype issuetype) {
        return GetJiraIssueType.builder()
                .id(issuetype.id)
                .name(issuetype.name)
                .description(issuetype.description)
                .build();
    }
}
