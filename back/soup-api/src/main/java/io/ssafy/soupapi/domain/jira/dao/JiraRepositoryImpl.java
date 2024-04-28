package io.ssafy.soupapi.domain.jira.dao;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.domain.project.mongodb.entity.Info;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JiraRepositoryImpl implements JiraRepository {
    private final ObjectMapper objectMapper;

    /**
     * 지라에 등록된 팀원 정보 로딩
     *
     * @param info
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public List<JiraUserDatum> findJiraTeamInfo(Info info) throws JsonProcessingException {
        HttpResponse<JsonNode> response = Unirest.get(String.format("https://%s.atlassian.net/rest/api/2/user/assignable/multiProjectSearch", info.getJiraHost()))
                .basicAuth(info.getJiraUsername(), info.getJiraKey())
                .header("Accept", "application/json")
                .queryString("projectKeys", info.getJiraProjectKey())
                .asJson();

        // 요청에 실패
        if (!(response.getStatus() == 200 || response.getStatus() == 201)) {
            throw new BaseExceptionHandler(ErrorCode.FAILED_TO_REQUEST_JIRA_API, getErrorResponse(response));
        }

        JiraUserDatum[] jiraUserData = objectMapper.readValue(response.getBody().toString(), JiraUserDatum[].class);
        return Arrays.stream(jiraUserData).toList();
    }

    private String getErrorResponse(HttpResponse<JsonNode> response) {
        var errors = response.getBody().getObject().getJSONArray("errorMessages");
        StringBuilder sb = new StringBuilder();
        errors.forEach(error -> sb.append(error).append(' '));
        return sb.toString();
    }
}
