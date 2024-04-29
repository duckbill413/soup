package io.ssafy.soupapi.domain.jira.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.soupapi.domain.jira.dto.Issue;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.domain.project.mongodb.entity.Info;

import java.util.List;

public interface JiraRepository {

    List<JiraUserDatum> findJiraTeamInfo(Info info) throws JsonProcessingException;

    List<Issue> findJiraIssues(Info jiraInfo) throws JsonProcessingException;
}
