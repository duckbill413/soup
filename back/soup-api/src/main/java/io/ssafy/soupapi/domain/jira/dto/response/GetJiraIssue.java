package io.ssafy.soupapi.domain.jira.dto.response;

import io.ssafy.soupapi.domain.jira.dto.Issue;
import io.ssafy.soupapi.global.util.StringParserUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Objects;

@Schema(description = "지라 이슈 정보")
public record GetJiraIssue(
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
        @Schema(description = "이슈 타입 (에픽, 스토리, 작업)")
        String issueTypeId,
        @Schema(description = "지라 이슈 상태")
        String status,
        @Schema(description = "지라 우선 순위")
        JiraPriority priority,
        @Schema(description = "지라 스토리 포인트")
        Long storyPoint,
        @Schema(description = "마지막 업데이트")
        String updated,
        @Schema(description = "상위 이슈 Id")
        String parentId,
        @Schema(description = "이슈 담당자")
        GetJiraUser assignee,
        @Schema(description = "이슈 보고자")
        GetJiraUser reporter
) {
    @Builder
    public GetJiraIssue {
        issueId = StringParserUtil.parseNullToEmpty(issueId);
        issueKey = StringParserUtil.parseNullToEmpty(issueKey);
        projectKey = StringParserUtil.parseNullToEmpty(projectKey);
        summary = StringParserUtil.parseNullToEmpty(summary);
        description = StringParserUtil.parseNullToEmpty(description);
        issueTypeId = StringParserUtil.parseNullToEmpty(issueTypeId);
        priority = Objects.nonNull(priority) ? priority : JiraPriority.MEDIUM;
        storyPoint = Objects.nonNull(storyPoint) ? storyPoint : 0L;
    }

    public static GetJiraIssue of(Issue issue) {
        return GetJiraIssue.builder()
                .issueId(issue.id)
                .issueKey(issue.key)
                .projectKey(issue.fields.project.key)
                .summary(issue.fields.summary)
                .description(issue.fields.description)
                .issueTypeId(issue.fields.issuetype.id)
                .updated(issue.fields.updated)
                .parentId(Objects.isNull(issue.fields.parent) ? null : issue.fields.parent.id)
                .priority(JiraPriority.valueOf(issue.fields.priority.name.toUpperCase()))
                .storyPoint(issue.fields.customfield_10031)
                .status(issue.fields.status.name)
                .assignee(io.ssafy.soupapi.domain.jira.dto.response.GetJiraUser.builder()
                        .id(issue.fields.assignee.accountId)
                        .email(issue.fields.assignee.emailAddress)
                        .name(issue.fields.assignee.displayName)
                        .build())
                .reporter(io.ssafy.soupapi.domain.jira.dto.response.GetJiraUser.builder()
                        .id(issue.fields.reporter.accountId)
                        .email(issue.fields.reporter.emailAddress)
                        .name(issue.fields.reporter.displayName)
                        .build())
                .build();
    }
}
