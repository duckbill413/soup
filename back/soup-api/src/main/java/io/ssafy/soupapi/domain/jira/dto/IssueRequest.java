package io.ssafy.soupapi.domain.jira.dto;

public record IssueRequest(
        String summary,
        String description,
        String projectId,
        String projectKey,
        String issueTypeId,
        String parentId,
        String priority,
        long storyPoint,
        String assigneeId,
        String reporterId
) {
}
