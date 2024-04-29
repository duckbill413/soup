package io.ssafy.soupapi.domain.jira.dao;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ssafy.soupapi.domain.jira.dto.Issue;
import io.ssafy.soupapi.domain.jira.dto.JiraIssues;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.domain.project.mongodb.entity.Info;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JiraRepositoryImpl implements JiraRepository {
    private final ObjectMapper objectMapper;

    /**
     * 지라에 등록된 팀원 정보 로딩
     *
     * @param projectInfo
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public List<JiraUserDatum> findJiraTeamInfo(Info projectInfo) throws JsonProcessingException {
        HttpResponse<JsonNode> response = Unirest.get(String.format("https://%s.atlassian.net/rest/api/2/user/assignable/multiProjectSearch", projectInfo.getJiraHost()))
                .basicAuth(projectInfo.getJiraUsername(), projectInfo.getJiraKey())
                .header("Accept", "application/json")
                .queryString("projectKeys", projectInfo.getJiraProjectKey())
                .asJson();

        // 요청에 실패
        if (!(response.getStatus() == 200 || response.getStatus() == 201)) {
            throw new BaseExceptionHandler(ErrorCode.FAILED_TO_REQUEST_JIRA_API, getErrorResponse(response));
        }

        JiraUserDatum[] jiraUserData = objectMapper.readValue(response.getBody().toString(), JiraUserDatum[].class);
        return Arrays.stream(jiraUserData).toList();
    }

    /**
     * 지라 프로젝트의 이슈 목록 조회
     *
     * @param projectInfo 프로젝트 지라 정보
     * @return 지라 프로젝트의 전체 이슈 정보
     * @throws JsonProcessingException json 파싱 실패 에러
     */
    @Override
    public List<Issue> findJiraIssues(Info projectInfo) throws JsonProcessingException {
        long startAt = 0;
        long total = 100;
        List<Issue> issues = new ArrayList<>();

        while (startAt < total) {
            JiraIssues jiraIssues = getJiraIssues(projectInfo, startAt);
            issues.addAll(jiraIssues.issues);

            startAt = jiraIssues.startAt + 100; // issue 조회 시작 시점 갱신
            total = jiraIssues.total; // 전체 이슈 개수 갱신
        }

        return issues;
    }

    /**
     * 지라에서 Issue 목록 조회
     *
     * @param projectInfo 프로젝트 정보
     * @param startAt     요청 시작하는 데이터
     * @return Jira Issue 목록
     * @throws JsonProcessingException json 파싱 실패 에러
     */
    private JiraIssues getJiraIssues(Info projectInfo, long startAt) throws JsonProcessingException {
        HttpResponse<JsonNode> response = Unirest.get(String.format("https://%s.atlassian.net/rest/api/2/search", projectInfo.getJiraHost()))
                .basicAuth(projectInfo.getJiraUsername(), projectInfo.getJiraKey())
                .header("Accept", "application/json")
                .queryString("startAt", startAt)
                .queryString("maxResults", 100)
                .queryString("jql", "project = %s".formatted(projectInfo.getJiraProjectKey()))
                .asJson();

        if (!(response.getStatus() == 200 || response.getStatus() == 201)) {
            throw new BaseExceptionHandler(ErrorCode.FAILED_TO_REQUEST_JIRA_API, getErrorResponse(response));
        }

        return objectMapper.readValue(response.getBody().toString(), JiraIssues.class);
    }

    private String getErrorResponse(HttpResponse<JsonNode> response) {
        var errors = response.getBody().getObject().getJSONArray("errorMessages");
        StringBuilder sb = new StringBuilder();
        errors.forEach(error -> sb.append(error).append(' '));
        return sb.toString();
    }
}
