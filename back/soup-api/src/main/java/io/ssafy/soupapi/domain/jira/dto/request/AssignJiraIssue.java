package io.ssafy.soupapi.domain.jira.dto.request;

import lombok.Builder;

@Builder
public record AssignJiraIssue(String accountId) {
}
