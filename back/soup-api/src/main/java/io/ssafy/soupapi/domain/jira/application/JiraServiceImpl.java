package io.ssafy.soupapi.domain.jira.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.soupapi.domain.jira.dao.JiraRepository;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.domain.jira.dto.response.JiraIssue;
import io.ssafy.soupapi.domain.jira.dto.response.GetJiraIssueType;
import io.ssafy.soupapi.domain.project.mongodb.dao.MProjectRepository;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JiraServiceImpl implements JiraService {
    private final JiraRepository jiraRepository;
    private final MProjectRepository mProjectRepository;

    /**
     * 지라에서 팀원 정보 찾기
     *
     * @param projectId 프로젝트 ID
     * @return 지라 팀원 정보
     */
    @Override
    public List<JiraUserDatum> findJiraTeamInfo(String projectId) {
        var jiraInfo = mProjectRepository.findProjectJiraInfo(new ObjectId(projectId)).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_JIRA_INFO)).getInfo();

        try {
            return jiraRepository.findJiraTeamInfo(jiraInfo);
        } catch (JsonProcessingException e) {
            throw new BaseExceptionHandler(ErrorCode.JSON_PARSE_ERROR);
        }
    }

    /**
     * 우리 프로젝트와 지라 프로젝트의 이슈를 동기화
     *
     *
     * @param projectId
     * @return
     */
    @Override
    public String syncJiraProjectByIssues(String projectId) {
        return null;
    }

    @Override
    public PageOffsetResponse<List<JiraIssue>> findJiraIssues(String projectId, PageOffsetRequest pageOffsetRequest) {
        var jiraInfo = mProjectRepository.findProjectJiraInfo(new ObjectId(projectId)).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_JIRA_INFO)).getInfo();
        try {
            return jiraRepository.findJiraIssues(jiraInfo, pageOffsetRequest);

        } catch (JsonProcessingException e) {
            throw new BaseExceptionHandler(ErrorCode.JSON_PARSE_ERROR);
        }
    }

    @Override
    public List<GetJiraIssueType> findJiraIssueTypes(String projectId) {
        var jiraInfo = mProjectRepository.findProjectJiraInfo(new ObjectId(projectId)).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_JIRA_INFO)).getInfo();
        try {
            return jiraRepository.findJiraIssueTypes(jiraInfo);

        } catch (JsonProcessingException e) {
            throw new BaseExceptionHandler(ErrorCode.JSON_PARSE_ERROR);
        }
    }
}
