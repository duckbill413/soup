package io.ssafy.soupapi.domain.jira.dto.response;

import io.ssafy.soupapi.domain.jira.dto.Issue;
import io.ssafy.soupapi.domain.project.mongodb.entity.issue.ProjectIssue;
import io.ssafy.soupapi.domain.project.mongodb.entity.issue.ProjectUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Objects;
import java.util.UUID;

@Builder
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
        long storyPoint,
        @Schema(description = "마지막 업데이트")
        String updated,
        @Schema(description = "상위 이슈 Id")
        String parentId,
        @Schema(description = "이슈 담당자")
        JiraUser assignee,
        @Schema(description = "이슈 보고자")
        JiraUser reporter
) {
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

    public static JiraIssue of(String projectId, ProjectIssue issue) {
        return JiraIssue.builder()
                .issueId(issue.getIssueId())
                .issueKey(issue.getIssueKey())
                .projectKey(projectId)
                .summary(issue.getSummary())
                .description(issue.getDescription())
                .issueType(getIssueType(issue.getIssueType()))
                .updated(issue.getUpdated())
                .parentId(issue.getParentId())
                .storyPoint(issue.getStoryPoint())
                .status(issue.getStatus())
                .assignee(Objects.isNull(issue.getAssignee()) ? null :
                        JiraUser.builder()
                                .id(issue.getAssignee().getId())
                                .email(issue.getAssignee().getEmail())
                                .name(issue.getAssignee().getName())
                                .build())
                .reporter(Objects.isNull(issue.getReporter()) ? null :
                        JiraUser.builder()
                                .id(issue.getReporter().getId())
                                .email(issue.getReporter().getEmail())
                                .name(issue.getReporter().getName())
                                .build())
                .build();
    }

    public static ProjectIssue to(JiraIssue issue) {
        return ProjectIssue.builder()
                .projectIssueId(UUID.randomUUID())
                .issueId(issue.issueId())
                .issueKey(issue.issueKey())
                .summary(issue.summary())
                .description(issue.description())
                .issueType(issue.issueType())
                .status(issue.status())
                .priority(issue.priority())
                .storyPoint(issue.storyPoint())
                .updated(issue.updated())
                .parentId(issue.parentId())
                .assignee(Objects.isNull(issue.assignee()) ? null :
                        ProjectUser.builder()
                                .id(issue.assignee().id())
                                .name(issue.assignee().name())
                                .email(issue.assignee().email())
                                .build())
                .reporter(Objects.isNull(issue.reporter()) ? null :
                        ProjectUser.builder()
                                .id(issue.reporter().id())
                                .name(issue.reporter().name())
                                .email(issue.reporter().email())
                                .build())
                .isIssueCreated(false)
                .isIssueDeleted(false)
                .isIssueUpdated(false)
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
