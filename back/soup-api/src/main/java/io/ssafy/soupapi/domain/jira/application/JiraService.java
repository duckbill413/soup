package io.ssafy.soupapi.domain.jira.application;

import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.domain.jira.dto.response.GetJiraIssueType;
import io.ssafy.soupapi.domain.jira.dto.response.JiraIssue;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import org.bson.types.ObjectId;

import java.util.List;

public interface JiraService {
    List<JiraUserDatum> findJiraTeamInfo(String projectId);

    String syncJiraProjectByIssues(ObjectId projectId);

    PageOffsetResponse<List<JiraIssue>> findJiraIssues(String projectId, PageOffsetRequest pageOffsetRequest);

    List<GetJiraIssueType> findJiraIssueTypes(String projectId);
}
