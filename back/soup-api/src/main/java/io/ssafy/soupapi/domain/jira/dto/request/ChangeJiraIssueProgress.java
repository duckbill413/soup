package io.ssafy.soupapi.domain.jira.dto.request;

import lombok.Builder;

@Builder
public record ChangeJiraIssueProgress(EditTransition transition) {
}
