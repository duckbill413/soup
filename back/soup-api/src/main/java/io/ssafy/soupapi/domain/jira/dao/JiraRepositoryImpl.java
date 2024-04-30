package io.ssafy.soupapi.domain.jira.dao;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ssafy.soupapi.domain.jira.dto.JiraIssueType;
import io.ssafy.soupapi.domain.jira.dto.JiraIssues;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.domain.jira.dto.response.GetJiraIssue;
import io.ssafy.soupapi.domain.jira.dto.response.GetJiraIssueType;
import io.ssafy.soupapi.domain.project.mongodb.entity.Info;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.OffsetPagination;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
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
    public PageOffsetResponse<List<GetJiraIssue>> findJiraIssues(Info projectInfo, PageOffsetRequest pageOffsetRequest) throws JsonProcessingException {
        int startAt = (pageOffsetRequest.page() - 1) * pageOffsetRequest.size();
        int maxResults = pageOffsetRequest.size();
        JiraIssues jiraIssues = getJiraIssues(projectInfo, startAt, maxResults);

        return PageOffsetResponse.<List<GetJiraIssue>>builder()
                .content(jiraIssues.getIssues().stream().map(GetJiraIssue::of).toList())
                .pagination(OffsetPagination.builder()
                        .page(pageOffsetRequest.page())
                        .size(pageOffsetRequest.size())
                        .total((int) (jiraIssues.getTotal() % jiraIssues.getMaxResults() == 0 ?
                                jiraIssues.getTotal() / jiraIssues.getMaxResults() :
                                jiraIssues.getTotal() / jiraIssues.getMaxResults() + 1))
                        .totalCount(jiraIssues.getTotal().intValue())
                        .build())
                .build();
    }

    @Override
    public List<GetJiraIssueType> findJiraIssueTypes(Info jiraInfo) throws JsonProcessingException {
        long startAt = 0;
        long total = 100;
        List<GetJiraIssueType> issueTypes = new ArrayList<>();

        while (startAt < total) {
            var jiraIssueType = getJiraIssueTypes(jiraInfo, startAt);
            issueTypes.addAll(jiraIssueType.getIssueTypes().stream().map(GetJiraIssueType::of).toList());

            startAt = jiraIssueType.getStartAt() + 100;
            total = jiraIssueType.getTotal();
        }

        return issueTypes;
    }

    private JiraIssueType getJiraIssueTypes(Info jiraInfo, long startAt) throws JsonProcessingException {
        HttpResponse<JsonNode> response = Unirest.get(String.format("https://%s.atlassian.net/rest/api/2/issue/createmeta/%s/issuetypes", jiraInfo.getJiraHost(), jiraInfo.getJiraProjectKey()))
                .basicAuth(jiraInfo.getJiraUsername(), jiraInfo.getJiraKey())
                .header("Accept", "application/json")
                .queryString("startAt", startAt)
                .queryString("maxResults", 100)
                .asJson();

        if (!(response.getStatus() == 200 || response.getStatus() == 201)) {
            throw new BaseExceptionHandler(ErrorCode.FAILED_TO_REQUEST_JIRA_API, getErrorResponse(response));
        }

        return objectMapper.readValue(response.getBody().toString(), JiraIssueType.class);
    }

    private void createJiraIssue() {
    }

    /**
     * 지라에서 Issue 목록 조회
     *
     * @param projectInfo 프로젝트 정보
     * @param startAt     요청 시작하는 데이터
     * @param maxResults
     * @return Jira Issue 목록
     * @throws JsonProcessingException json 파싱 실패 에러
     */
    private JiraIssues getJiraIssues(Info projectInfo, int startAt, int maxResults) throws JsonProcessingException {
        HttpResponse<JsonNode> response = Unirest.get(String.format("https://%s.atlassian.net/rest/api/2/search", projectInfo.getJiraHost()))
                .basicAuth(projectInfo.getJiraUsername(), projectInfo.getJiraKey())
                .header("Accept", "application/json")
                .queryString("startAt", startAt)
                .queryString("maxResults", maxResults)
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
