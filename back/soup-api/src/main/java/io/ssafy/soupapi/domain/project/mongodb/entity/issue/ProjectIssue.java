package io.ssafy.soupapi.domain.project.mongodb.entity.issue;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ProjectIssue {
    @Field("project_issue_id")
    private UUID projectIssueId;
    @Field("project_jira_issue_id")
    private String issueId;
    @Field("project_jira_issue_key")
    private String issueKey;
    @Field("project_issue_summary")
    private String summary;
    @Field("project_issue_description")
    private String description;
    @Field("project_issue_type")
    private String issueType;
    @Field("project_issue_status")
    private String status;
    @Field("project_issue_priority")
    private String priority;
    @Field("project_issue_story_point")
    private Long storyPoint;
    @Field("project_issue_updated")
    private String updated;
    @Field("project_parent_issue_id")
    private String parentId;
    @Field("project_issue_assignee")
    private ProjectUser assignee;
    @Field("project_issue_reporter")
    private ProjectUser reporter;
    @Field("project_issue_is_created")
    private boolean isIssueCreated;
    @Field("project_issue_is_updated")
    private boolean isIssueUpdated;
    @Field("project_issue_is_deleted")
    private boolean isIssueDeleted;
}
