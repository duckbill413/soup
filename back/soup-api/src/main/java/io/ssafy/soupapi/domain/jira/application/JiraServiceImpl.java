package io.ssafy.soupapi.domain.jira.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.soupapi.domain.jira.dao.JiraRepository;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.domain.jira.dto.response.GetJiraIssue;
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
     * 0. 지라 이슈 목록 불러오기
     * - 지라에 등록된 이슈들의 리스트를 가져온다.
     * 1. 등록
     * - 우리 프로젝트의 이슈 중 지라 ID가 없는 이슈에 대해서 지라에 이슈를 등록
     * - updated 를 false 로 설정
     * - 지라 이슈중 우리 프로젝트에 없는 경우 우리 프로젝트 이슈 목록에 등록
     * 2. 업데이트
     * - 우리 프로젝트의 이슈 중 지라 ID가 있으면서 updated가 true 인 경우 지라 이슈를 업데이트
     * 3. 삭제
     * - 우리 프로젝트 이슈 중에서 status가 false인 이슈들에 대하여 JIRA ID 값이 있지만 지라 issue 목록에는 ID가 없는 경우
     * - 해당 이슈를 지라의 이슈에서 삭제 처리
     *
     * @param projectId
     * @return
     */
    @Override
    public String syncJiraProjectByIssues(String projectId) {
        return null;
    }

    @Override
    public PageOffsetResponse<List<GetJiraIssue>> findJiraIssues(String projectId, PageOffsetRequest pageOffsetRequest) {
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
