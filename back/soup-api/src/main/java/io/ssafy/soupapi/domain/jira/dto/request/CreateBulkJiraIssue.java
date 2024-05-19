
package io.ssafy.soupapi.domain.jira.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateBulkJiraIssue(List<EditIssueUpdate> issueUpdates) {

}
