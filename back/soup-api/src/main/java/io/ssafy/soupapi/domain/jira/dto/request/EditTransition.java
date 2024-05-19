
package io.ssafy.soupapi.domain.jira.dto.request;

import io.ssafy.soupapi.domain.jira.dto.response.JiraIssue;
import lombok.Builder;

@Builder
public record EditTransition(String id) { // progress
    public static EditTransition of(JiraIssue jiraIssue) {
        return EditTransition.builder()
                .id(getProgressCode(jiraIssue.status()))
                .build();
    }

    private static String getProgressCode(String progress) {
        return switch (progress.toUpperCase()) {
            case "해야 할 일", "TO DO" -> "11";
            case "진행중", "IN PROGRESS" -> "21";
            case "완료", "DONE" -> "31";
            default -> progress;
        };
    }
}
