package io.ssafy.soupapi.domain.jira.dao;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.ssafy.soupapi.domain.jira.dto.CreatedJiraIssues;
import io.ssafy.soupapi.domain.jira.dto.JiraIssueType;
import io.ssafy.soupapi.domain.jira.dto.JiraIssues;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.domain.jira.dto.request.*;
import io.ssafy.soupapi.domain.jira.dto.response.GetJiraIssueType;
import io.ssafy.soupapi.domain.jira.dto.response.JiraIssue;
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
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class JiraRepositoryImpl implements JiraRepository {
    private final ObjectMapper objectMapper;
    private final Gson gson;

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
        checkResponseSuccess(response);

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
    public PageOffsetResponse<List<JiraIssue>> findJiraIssues(Info projectInfo, PageOffsetRequest pageOffsetRequest) throws JsonProcessingException {
        int startAt = (pageOffsetRequest.page() - 1) * pageOffsetRequest.size();
        int maxResults = pageOffsetRequest.size();
        JiraIssues jiraIssues = getJiraIssues(projectInfo, startAt, maxResults);

        return PageOffsetResponse.<List<JiraIssue>>builder()
                .content(jiraIssues.getIssues().stream().map(JiraIssue::of).toList())
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
    public List<JiraIssue> findAllJiraIssues(Info projectInfo) {
        long startAt = 0;
        long maxResults = 100;
        long total = 100;

        List<JiraIssue> issues = new ArrayList<>();
        while (startAt < total) {
            JiraIssues jiraIssues = null;
            try {
                jiraIssues = getJiraIssues(projectInfo, (int) startAt, (int) maxResults);
            } catch (JsonProcessingException e) {
                break;
            }
            startAt = jiraIssues.startAt + maxResults;
            total = jiraIssues.getTotal();
            issues.addAll(jiraIssues.getIssues().stream().map(JiraIssue::of).toList());
        }
        return issues;
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

    @Override
    public int createBulkJiraIssue(Info jiraInfo, List<JiraIssue> jiraIssues) throws JsonProcessingException {
        List<EditIssueUpdate> issueUpdates = new ArrayList<>();

        for (JiraIssue jiraIssue : jiraIssues) {
            issueUpdates.add(EditIssueUpdate.builder()
                    .fields(EditFields.of(jiraIssue))
                    .transition(EditTransition.of(jiraIssue))
                    .build());
        }

        var requestBody = CreateBulkJiraIssue.builder()
                .issueUpdates(issueUpdates)
                .build();

        HttpResponse<JsonNode> response = Unirest.post(String.format("https://%s.atlassian.net/rest/api/2/issue/bulk", jiraInfo.getJiraHost()))
                .basicAuth(jiraInfo.getJiraUsername(), jiraInfo.getJiraKey())
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(gson.toJson(requestBody))
                .asJson();

        checkResponseSuccess(response);

        CreatedJiraIssues createJiraIssues = objectMapper.readValue(response.getBody().toString(), CreatedJiraIssues.class);
        return createJiraIssues.getIssues().size();
    }

    @Override
    public int createJiraIssue(Info jiraInfo, JiraIssue jiraIssue) {
        var requestBody = EditIssueUpdate.builder()
                .fields(EditFields.of(jiraIssue))
                .transition(EditTransition.of(jiraIssue))
                .build();

        HttpResponse<JsonNode> response = Unirest.post(String.format("https://%s.atlassian.net/rest/api/2/issue", jiraInfo.getJiraHost()))
                .basicAuth(jiraInfo.getJiraUsername(), jiraInfo.getJiraKey())
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(gson.toJson(requestBody))
                .asJson();

        checkResponseSuccess(response);

        return 1;
    }

    @Override
    public void changeJiraIssue(Info jiraInfo, JiraIssue jiraIssue) {
        var requestBody = EditIssueUpdate.builder()
                .fields(EditFields.of(jiraIssue))
                .transition(EditTransition.of(jiraIssue))
                .build();

        HttpResponse<JsonNode> response = Unirest.put(String.format("https://%s.atlassian.net/rest/api/2/issue/%s", jiraInfo.getJiraHost(), jiraIssue.issueKey()))
                .basicAuth(jiraInfo.getJiraUsername(), jiraInfo.getJiraKey())
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(gson.toJson(requestBody))
                .asJson();

        checkResponseSuccess(response);
    }

    @Override
    public void changeJiraIssueProgress(Info jiraInfo, JiraIssue jiraIssue) {
        var requestBody = ChangeJiraIssueProgress.builder()
                .transition(EditTransition.of(jiraIssue))
                .build();

        HttpResponse<JsonNode> response = Unirest.post(String.format("https://%s.atlassian.net/rest/api/2/issue/%s/transitions", jiraInfo.getJiraHost(), jiraIssue.issueKey()))
                .basicAuth(jiraInfo.getJiraUsername(), jiraInfo.getJiraKey())
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(gson.toJson(requestBody))
                .asJson();

        checkResponseSuccess(response);
    }

    @Override
    public void assignJiraIssue(Info jiraInfo, JiraIssue jiraIssue) {
        if (Objects.isNull(jiraIssue.assignee())) {
            throw new BaseExceptionHandler(ErrorCode.FAILED_TO_UPDATE_JIRA_ISSUE);
        }
        var requestBody = AssignJiraIssue.builder()
                .accountId(jiraIssue.assignee().id())
                .build();

        HttpResponse<JsonNode> response = Unirest.put(String.format("https://%s.atlassian.net/rest/api/2/issue/%s/assignee", jiraInfo.getJiraHost(), jiraIssue.issueKey()))
                .basicAuth(jiraInfo.getJiraUsername(), jiraInfo.getJiraKey())
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(gson.toJson(requestBody))
                .asJson();

        checkResponseSuccess(response);
    }

    @Override
    public void deleteJiraIssue(Info jiraInfo, JiraIssue jiraIssue) {
        Unirest.delete(String.format("https://%s.atlassian.net/rest/api/2/issue/%s", jiraInfo.getJiraHost(), jiraIssue.issueKey()))
                .basicAuth(jiraInfo.getJiraUsername(), jiraInfo.getJiraKey())
                .asString();
    }


    private JiraIssueType getJiraIssueTypes(Info jiraInfo, long startAt) throws JsonProcessingException {
        HttpResponse<JsonNode> response = Unirest.get(String.format("https://%s.atlassian.net/rest/api/2/issue/createmeta/%s/issuetypes", jiraInfo.getJiraHost(), jiraInfo.getJiraProjectKey()))
                .basicAuth(jiraInfo.getJiraUsername(), jiraInfo.getJiraKey())
                .header("Accept", "application/json")
                .queryString("startAt", startAt)
                .queryString("maxResults", 100)
                .asJson();

        checkResponseSuccess(response);

        return objectMapper.readValue(response.getBody().toString(), JiraIssueType.class);
    }

    private void checkResponseSuccess(HttpResponse<JsonNode> response) {
        if (!(response.getStatus() == 200 || response.getStatus() == 201 || response.getStatus() == 204)) {
            throw new BaseExceptionHandler(ErrorCode.FAILED_TO_REQUEST_JIRA_API, getErrorResponse(response));
        }
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

        checkResponseSuccess(response);

        return objectMapper.readValue(response.getBody().toString(), JiraIssues.class);
    }

    private String getErrorResponse(HttpResponse<JsonNode> response) {
        var errors = response.getBody().getObject().getJSONArray("errorMessages");
        StringBuilder sb = new StringBuilder();
        errors.forEach(error -> sb.append(error).append(' '));
        return sb.toString();
    }
}
