package io.ssafy.soupapi.domain.jira.dto;

import io.ssafy.soupapi.domain.jira.dto.response.JiraPriority;

public record IssueRequest(
        String summary,
        String description,
        String projectId,
        String projectKey,
        String issueTypeId,
        String parentId,
        JiraPriority priority,
        long storyPoint,
        String assigneeId,
        String reporterId
) {
}
