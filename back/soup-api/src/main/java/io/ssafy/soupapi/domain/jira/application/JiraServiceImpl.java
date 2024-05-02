package io.ssafy.soupapi.domain.jira.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.soupapi.domain.jira.dao.JiraRepository;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.domain.jira.dto.response.GetJiraIssueType;
import io.ssafy.soupapi.domain.jira.dto.response.JiraIssue;
import io.ssafy.soupapi.domain.project.mongodb.dao.MProjectRepository;
import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.issue.ProjectIssue;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class JiraServiceImpl implements JiraService {
    private final JiraRepository jiraRepository;
    private final MProjectRepository mProjectRepository;
    private final MongoTemplate mongoTemplate;

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
     * 1. 우리 프로젝트 이슈 목록중 isCreated, isUpdated, isDeleted가 true인 데이터를 불러온다.
     * 2. isCreated 가 true 인 값들에 대해서 insert 를 수행
     * 3. isDeleted 가 true 인 값들에 대해서 삭제 처리 한다.
     * 4. isUpdated 가 true 인 값들에 대해서 업데이트 처리
     * 5. local의 모든 데이터를 삭제한뒤 Jira 데이터로 replace
     *
     * @param projectId
     * @return
     */
    @Transactional
    @Override
    public String syncJiraProjectByIssues(ObjectId projectId) {
        var projectInfo = mProjectRepository.findProjectJiraInfo(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));
        var projectIssues = mProjectRepository.findAllProjectIssues(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));

        if (Objects.isNull(projectIssues.getIssues())) {
            List<JiraIssue> allJiraIssues = jiraRepository.findAllJiraIssues(projectInfo.getInfo());
            Query query = new Query().addCriteria(Criteria.where("_id").is(projectId));
            Update update = new Update().set("project_issues", allJiraIssues.stream().map(JiraIssue::to).toList());
            mongoTemplate.updateFirst(query, update, Project.class);
            return "동기화 완료";
        }

        var issues = projectIssues.getIssues();
        List<ProjectIssue> createdIssue = new ArrayList<>();
        List<ProjectIssue> deletedIssue = new ArrayList<>();
        List<ProjectIssue> updatedIssue = new ArrayList<>();
        List<ProjectIssue> needSave = new ArrayList<>();

        for (ProjectIssue issue : issues) {
            if (issue.isIssueCreated()) {
                createdIssue.add(issue);
                continue;
            }
            // 연동된 상태이면서 삭제된 경우
            if (Objects.nonNull(issue.getIssueKey()) && issue.isIssueDeleted()) {
                deletedIssue.add(issue);
                continue;
            }
            if (issue.isIssueUpdated()) {
                updatedIssue.add(issue);
                continue;
            }
            needSave.add(issue);
        }

        // 이슈 생성
        for (ProjectIssue projectIssue : createdIssue) {
            try {
                jiraRepository.createJiraIssue(projectInfo.getInfo(), JiraIssue.of(projectId.toHexString(), projectIssue));
            } catch (Exception e) {
                log.info(projectId + "동기화 실패");
            }
        }

        for (ProjectIssue projectIssue : deletedIssue) {
            try {
                jiraRepository.deleteJiraIssue(projectInfo.getInfo(), JiraIssue.of(projectId.toHexString(), projectIssue));
            } catch (Exception e) {
                log.info(projectId + "동기화 실패");
            }
        }

        for (ProjectIssue projectIssue : updatedIssue) {
            try {
                jiraRepository.changeJiraIssue(projectInfo.getInfo(), JiraIssue.of(projectId.toHexString(), projectIssue));
                jiraRepository.changeJiraIssueProgress(projectInfo.getInfo(), JiraIssue.of(projectId.toHexString(), projectIssue));
            } catch (Exception e) {
                log.info(projectId + "동기화 실패");
            }
        }

        List<JiraIssue> allJiraIssues = jiraRepository.findAllJiraIssues(projectInfo.getInfo());
        Query query = new Query().addCriteria(Criteria.where("_id").is(projectId));
        needSave.addAll(allJiraIssues.stream().map(JiraIssue::to).toList());
        Update update = new Update().set("project_issues", needSave);
        mongoTemplate.updateFirst(query, update, Project.class);

        return "동기화 완료";
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
