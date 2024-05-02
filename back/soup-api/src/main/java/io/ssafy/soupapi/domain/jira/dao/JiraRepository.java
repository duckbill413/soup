package io.ssafy.soupapi.domain.jira.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.domain.jira.dto.response.JiraIssue;
import io.ssafy.soupapi.domain.jira.dto.response.GetJiraIssueType;
import io.ssafy.soupapi.domain.project.mongodb.entity.Info;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;

import java.util.List;

public interface JiraRepository {

    List<JiraUserDatum> findJiraTeamInfo(Info info) throws JsonProcessingException;

    PageOffsetResponse<List<JiraIssue>> findJiraIssues(Info jiraInfo, PageOffsetRequest pageOffsetRequest) throws JsonProcessingException;
    List<JiraIssue> findAllJiraIssues(Info jiraInfo);

    List<GetJiraIssueType> findJiraIssueTypes(Info jiraInfo) throws JsonProcessingException;
    int createBulkJiraIssue(Info jiraInfo, List<JiraIssue> jiraIssues) throws JsonProcessingException;
    int createJiraIssue(Info jiraInfo, JiraIssue jiraIssue);
    void changeJiraIssue(Info jiraInfo, JiraIssue jiraIssue);
    void changeJiraIssueProgress(Info jiraInfo, JiraIssue jiraIssue);
    void assignJiraIssue(Info jiraInfo, JiraIssue jiraIssue);
    void deleteJiraIssue(Info jiraInfo, JiraIssue jiraIssue);
}
