package io.ssafy.soupapi.domain.jira.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.domain.project.mongodb.entity.Info;
import io.ssafy.soupapi.domain.project.mongodb.entity.Project;

import java.util.List;

public interface JiraRepository {

    List<JiraUserDatum> findJiraTeamInfo(Info info) throws JsonProcessingException;
}
