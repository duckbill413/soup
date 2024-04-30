package io.ssafy.soupapi.domain.jira.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.domain.jira.dto.response.GetJiraIssue;
import io.ssafy.soupapi.domain.jira.dto.response.GetJiraIssueType;
import io.ssafy.soupapi.domain.project.mongodb.entity.Info;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;

import java.util.List;

public interface JiraRepository {

    List<JiraUserDatum> findJiraTeamInfo(Info info) throws JsonProcessingException;

    PageOffsetResponse<List<GetJiraIssue>> findJiraIssues(Info jiraInfo, PageOffsetRequest pageOffsetRequest) throws JsonProcessingException;

    List<GetJiraIssueType> findJiraIssueTypes(Info jiraInfo) throws JsonProcessingException;
}
