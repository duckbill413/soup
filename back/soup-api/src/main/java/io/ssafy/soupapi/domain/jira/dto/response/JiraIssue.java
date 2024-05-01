package io.ssafy.soupapi.domain.jira.dto.response;

import io.ssafy.soupapi.domain.jira.dto.Issue;
import io.ssafy.soupapi.global.util.StringParserUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Objects;

@Schema(description = "지라 이슈 정보")
public record JiraIssue(
        @Schema(description = "지라 이슈 ID")
        String issueId,
        @Schema(description = "지라 이슈 Key")
        String issueKey,
        @Schema(description = "지라 프로젝트 key")
        String projectKey,
        @Schema(description = "이슈 제목")
        String summary,
        @Schema(description = "이슈 설명")
        String description,
        @Schema(description = "이슈 타입 (에픽, 스토리, 작업)", allowableValues = {"10000", "10001", "10002"})
        String issueType,
        @Schema(description = "지라 이슈 상태")
        String status,
        @Schema(description = "지라 우선 순위")
        String priority,
        @Schema(description = "지라 스토리 포인트")
        Long storyPoint,
        @Schema(description = "마지막 업데이트")
        String updated,
        @Schema(description = "상위 이슈 Id")
        String parentId,
        @Schema(description = "이슈 담당자")
        JiraUser assignee,
        @Schema(description = "이슈 보고자")
        JiraUser reporter,
        boolean updatedValue
) {
    @Builder
    public JiraIssue {
        issueId = StringParserUtil.parseNullToEmpty(issueId);
        issueKey = StringParserUtil.parseNullToEmpty(issueKey);
        projectKey = StringParserUtil.parseNullToEmpty(projectKey);
        summary = StringParserUtil.parseNullToEmpty(summary);
        description = StringParserUtil.parseNullToEmpty(description);
        issueType = StringParserUtil.parseNullToEmpty(issueType);
        priority = StringParserUtil.parseNullToEmpty(priority);
        storyPoint = Objects.nonNull(storyPoint) ? storyPoint : 0L;
    }

    public static JiraIssue of(Issue issue) {
        return JiraIssue.builder()
                .issueId(Objects.nonNull(issue.id) ? issue.id : "")
                .issueKey(Objects.nonNull(issue.key) ? issue.key : "")
                .projectKey(Objects.nonNull(issue.fields.project.key) ? issue.fields.project.key : "")
                .summary(issue.fields.summary)
                .description(Objects.nonNull(issue.fields.description) ? issue.fields.description : "")
                .issueType(getIssueType(issue.fields.issuetype.id))
                .updated(issue.fields.updated)
                .parentId(Objects.isNull(issue.fields.parent) ? null : issue.fields.parent.id)
                .priority(issue.fields.priority.name)
                .storyPoint(Objects.nonNull(issue.fields.customfield_10031) ? issue.fields.customfield_10031 : 0)
                .status(issue.fields.status.name)
                .assignee(Objects.nonNull(issue.fields.assignee) ?
                        JiraUser.builder()
                                .id(issue.fields.assignee.accountId)
                                .email(issue.fields.assignee.emailAddress)
                                .name(issue.fields.assignee.displayName)
                                .build() : null)
                .reporter(Objects.nonNull(issue.fields.reporter) ? JiraUser.builder()
                        .id(issue.fields.reporter.accountId)
                        .email(issue.fields.reporter.emailAddress)
                        .name(issue.fields.reporter.displayName)
                        .build() : null)
                .build();
    }

    private static String getIssueType(String issueType) {
        return switch (issueType) {
            case "10000" -> "에픽";
            case "10001" -> "스토리";
            case "10002" -> "작업";
            default -> issueType;
        };
    }
}
