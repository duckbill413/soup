package io.ssafy.soupapi.domain.jira.application;

import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;

import java.util.List;

public interface JiraService {
    List<JiraUserDatum> findJiraTeamInfo(String projectId);
}
