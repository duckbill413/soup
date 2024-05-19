
package io.ssafy.soupapi.domain.jira.dto.request;

import io.ssafy.soupapi.domain.jira.dto.response.JiraIssue;
import lombok.Builder;

import java.util.Objects;

@Builder
public record EditFields(String summary, String description, EditProject project, EditIssuetype issuetype,
                         EditParent parent, EditPriority priority, Long customfield_10031, EditAssignee assignee,
                         EditReporter reporter) {

    public static EditFields of(JiraIssue jiraIssue) {
        return EditFields.builder()
                .summary(jiraIssue.summary())
                .description(jiraIssue.description())
                .project(new EditProject(jiraIssue.projectKey()))
                .issuetype(new EditIssuetype(getIssueTypeCode(jiraIssue.issueType())))
                .parent(new EditParent(jiraIssue.parentId()))
                .priority(new EditPriority(getIssuePriorityCode(jiraIssue.priority())))
                .customfield_10031(jiraIssue.storyPoint())
                .assignee(Objects.nonNull(jiraIssue.assignee()) ? new EditAssignee(jiraIssue.assignee().id()) : null)
                .reporter(Objects.nonNull(jiraIssue.reporter()) ? new EditReporter(jiraIssue.reporter().id()) : null)
                .build();
    }

    private static String getIssueTypeCode(String issueType) {
        return switch (issueType) {
            case "에픽" -> "10000";
            case "스토리" -> "10001";
            case "작업" -> "10002";
            default -> issueType;
        };
    }

    private static String getIssuePriorityCode(String priority) {
        return switch (priority.toUpperCase()) {
            case "LOWEST" -> "5";
            case "LOW" -> "4";
            case "MEDIUM" -> "3";
            case "HIGH" -> "2";
            case "HIGHEST" -> "1";
            default -> priority;
        };
    }
}
