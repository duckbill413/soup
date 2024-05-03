package io.ssafy.soupapi.domain.project.mongodb.application;

import com.google.gson.Gson;
import io.ssafy.soupapi.domain.project.mongodb.dao.MProjectRepository;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectProposal;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectTool;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.*;
import io.ssafy.soupapi.domain.project.mongodb.entity.Info;
import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.entity.issue.ProjectIssue;
import io.ssafy.soupapi.domain.project.mongodb.entity.vuerd.VuerdDoc;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.OffsetPagination;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class MProjectServiceImpl implements MProjectService {
    private final MProjectRepository mProjectRepository;
    private final MongoTemplate mongoTemplate;
    private final Gson gson;

    /**
     * 프로젝트 생성 및 최초 팀 구성 설정
     * 최초 생성자 권한을 ADMIN으로 지정
     *
     * @param userSecurityDTO project maker
     * @return mongodb project object id
     */
    @Transactional
    @Override
    public ObjectId createProject(UserSecurityDTO userSecurityDTO) { // TODO: member security 적용
        // 프로젝트 및 프로젝트 이름 설정
        var project = Project.builder()
                .info(
                        Info.builder()
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now())
                                .build()
                ).build();

        return mProjectRepository.save(project).getId();
    }

    /**
     * Project Info 정보 반환
     *
     * @param projectId mongodb project id
     * @return ProjectInfoDto that key info is null
     */
    @Transactional(readOnly = true)
    @Override
    public GetProjectInfo findProjectInfoAndTools(ObjectId projectId) {
        var project = mProjectRepository.findInfoAndToolsById(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));
        return GetProjectInfo.toProjectInfoDto(project);
    }

    /**
     * ProjectProposalDto 반환
     *
     * @param projectId mongodb project id
     * @return ProjectProposalDto
     */
    @Transactional(readOnly = true)
    @Override
    public GetProjectProposal findProjectProposal(ObjectId projectId) {
        var project = mProjectRepository.findProposalById(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));
        return GetProjectProposal.toProjectProposalDto(projectId, project.getProposal());
    }

    /**
     * 프로젝트 제안서 업데이트
     *
     * @param updateProjectProposal proposal update data
     * @return ProjectProposalDto
     */
    @Transactional
    @Override
    public GetProjectProposal updateProjectProposal(ObjectId projectId, UpdateProjectProposal updateProjectProposal) {
        mProjectRepository.updateProposal(projectId,
                UpdateProjectProposal.toProjectProposal(updateProjectProposal));
        return findProjectProposal(projectId);
    }

    /**
     * 프로젝트 정보(개요) 업데이트
     * - 이미지 정보, JIRA 정보는 업데이트 되지 않음
     *
     * @param projectId         업데이트 하는 프로젝트의 Id
     * @param updateProjectInfo 업데이트할 프로젝트 정보
     * @return 업데이트 완료 후 프로젝트 정보
     */
    @Transactional
    @Override
    public GetProjectInfo updateProjectInfo(ObjectId projectId, UpdateProjectInfo updateProjectInfo) {
        var project = mProjectRepository.findInfoAndToolsById(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));

        // update info
        project.setInfo(Info.builder()
                .name(updateProjectInfo.name())
                .description(updateProjectInfo.description())
                .startDate(updateProjectInfo.getStartDate())
                .endDate(updateProjectInfo.getEndDate())
                .build());
        // update tools
        var tools = updateProjectInfo.tools().stream().map(UpdateProjectTool::toTool).toList();
        project.setTools(tools);

        mProjectRepository.save(project);
        return GetProjectInfo.toProjectInfoDto(project);
    }

    /**
     * 프로젝트 지라 키 검색
     *
     * @param projectId 지라 키를 찾을 프로젝트 Id
     * @return 지라 유저 이름 및 키 정보
     */
    @Override
    public GetProjectJiraKey findProjectJiraKey(ObjectId projectId) {
        var project = mProjectRepository.findProjectJiraInfo(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));
        return GetProjectJiraKey.toProjectInfoDto(project.getInfo());
    }

    /**
     * 프로젝트 지라 키 정보 업데이트
     *
     * @param projectId            지라 키 정보를 업데이트할 프로젝트 Id
     * @param updateProjectJiraKey 업데이트할 프로젝트 자라 정보
     * @return 업데이트 완료된 프로젝트 지라 정보
     */
    @Override
    public GetProjectJiraKey updateProjectJiraKey(ObjectId projectId, UpdateProjectJiraKey updateProjectJiraKey) {
        var project = mProjectRepository.findProjectJiraInfo(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.FAILED_TO_UPDATE_PROJECT));
        project.getInfo().setJiraHost(updateProjectJiraKey.host());
        project.getInfo().setJiraProjectKey(updateProjectJiraKey.projectKey());
        project.getInfo().setJiraUsername(updateProjectJiraKey.username());
        project.getInfo().setJiraKey(updateProjectJiraKey.key());
        mProjectRepository.save(project);
        return GetProjectJiraKey.toProjectInfoDto(project.getInfo());
    }

    @Override
    public PageOffsetResponse<List<ProjectIssue>> findProjectIssues(ObjectId projectId, PageOffsetRequest pageOffsetRequest) {
        int offset = (pageOffsetRequest.page() - 1) * pageOffsetRequest.size();
        int limit = pageOffsetRequest.size();
        var projectIssues = mProjectRepository.findProjectIssues(projectId, offset, limit).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.FAILED_TO_UPDATE_PROJECT)).getIssues();

        // 저장된 이슈가 없는 경우
        if (Objects.isNull(projectIssues)) {
            projectIssues = List.of();
        }

        var issueCount = (int) countTotalProjectIssues(projectId);
        return PageOffsetResponse.<List<ProjectIssue>>builder()
                .content(projectIssues)
                .pagination(OffsetPagination.builder()
                        .page(pageOffsetRequest.page())
                        .size(pageOffsetRequest.size())
                        .total(issueCount % pageOffsetRequest.size() == 0 ?
                                issueCount / pageOffsetRequest.size() :
                                issueCount / pageOffsetRequest.size() + 1)
                        .totalCount(issueCount)
                        .build())
                .build();
    }

    @Override
    public PageOffsetResponse<List<ProjectIssue>> updateProjectIssues(ObjectId projectId, List<ProjectIssue> issues, PageOffsetRequest pageOffsetRequest, UserSecurityDTO userSecurityDTO) {
        for (ProjectIssue issue : issues) {
            // 변경 사항이 없는 경우
            if (!issue.isIssueUpdated()) {
                continue;
            }

            // 새로 저장되는 데이터의 경우
            if (Objects.isNull(issue.getProjectIssueId())) {
                issue.setProjectIssueId(UUID.randomUUID());
                issue.setIssueCreated(true);
                insertProjectIssue(projectId, issue);
                continue;
            }

            // 업데이트 되는 데이터의 경우
            updateProjectIssue(projectId, issue);
        }
        return findProjectIssues(projectId, pageOffsetRequest);
    }

    @Override
    public VuerdDoc findProjectVuerd(ObjectId projectId) {
        var project = mProjectRepository.findVuerdById(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));
        if (Objects.isNull(project.getVuerdDoc())) {
            VuerdDoc sampleVuerd = getSampleVuerdDoc();
            mProjectRepository.changeVuerdById(projectId, sampleVuerd);
            return sampleVuerd;
        }

        return project.getVuerdDoc();
    }

    @Override
    public VuerdDoc changeProjectVuerd(ObjectId projectId, VuerdDoc vuerdDoc) {
        var project = mProjectRepository.findVuerdById(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));

        mProjectRepository.changeVuerdById(projectId, vuerdDoc);
        return vuerdDoc;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetSimpleApiDoc> findProjectApiDocs(ObjectId projectId) {
        var project = mProjectRepository.findProjectApiDocs(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));

        if (Objects.isNull(project.getApiDocs()) || Objects.isNull(project.getApiDocs().getApiDocList())) {
            return List.of();
        }

        var apiDocs = project.getApiDocs().getApiDocList();
        return apiDocs.stream().map(GetSimpleApiDoc::of).toList();
    }

    @Override
    public List<String> findProjectValidPathVariableNames(ObjectId projectId, UUID apiDocId) {
        Query query = new Query().addCriteria(Criteria.where("_id").is(projectId)
                .and("project_api_doc.api_docs.id").is(apiDocId));
        var apiDoc = mongoTemplate.findOne(query, ApiDoc.class);
        if (Objects.isNull(apiDoc)) {
            throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_API_DOC);
        }
        return apiDoc.getValidPathVariableNames();
    }

    @Override
    public GetApiDoc findProjectSingleApiDocs(ObjectId projectId, String apiDocId) {
        Query query = new Query().addCriteria(Criteria.where("_id").is(projectId)
                .and("project_api_doc.api_docs.id").is(apiDocId));
        var apiDoc = mongoTemplate.findOne(query, ApiDoc.class);
        if (Objects.isNull(apiDoc)) {
            throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_API_DOC);
        }
        return GetApiDoc.of(apiDoc);
    }

    private void deleteProjectIssue(ObjectId projectId, ProjectIssue issue) {
        if (Objects.isNull(issue.getProjectIssueId())) {
            return;
        }
        Query query = new Query(Criteria.where("_id").is(projectId)
                .and("project_issues.project_issue_id").is(issue.getProjectIssueId()));

        Update update = new Update().pull("project_issues", Query.query(Criteria.where("project_issue_id").is(issue.getProjectIssueId())));

        mongoTemplate.updateFirst(query, update, Project.class);
    }

    private void insertProjectIssue(ObjectId projectId, ProjectIssue projectIssue) {
        Query query = new Query().addCriteria(Criteria.where("_id").is(projectId));
        Update update = new Update().addToSet("project_issues", projectIssue);
        mongoTemplate.updateFirst(query, update, Project.class);
    }

    public void updateProjectIssue(ObjectId projectId, ProjectIssue projectIssue) {
        Query query = new Query(Criteria.where("_id").is(projectId)
                .and("project_issues.project_issue_id").is(projectIssue.getProjectIssueId()));
        Update update = new Update()
                .set("project_issues.$.project_jira_issue_id", projectIssue.getIssueId())
                .set("project_issues.$.project_jira_issue_key", projectIssue.getIssueKey())
                .set("project_issues.$.project_issue_summary", projectIssue.getSummary())
                .set("project_issues.$.project_issue_description", projectIssue.getDescription())
                .set("project_issues.$.project_issue_type", projectIssue.getIssueType())
                .set("project_issues.$.project_issue_status", projectIssue.getStatus())
                .set("project_issues.$.project_issue_priority", projectIssue.getPriority())
                .set("project_issues.$.project_issue_story_point", projectIssue.getStoryPoint())
                .set("project_issues.$.project_issue_updated", projectIssue.getUpdated())
                .set("project_issues.$.project_parent_issue_id", projectIssue.getParentId())
                .set("project_issues.$.project_issue_assignee", projectIssue.getAssignee())
                .set("project_issues.$.project_issue_reporter", projectIssue.getReporter())
                .set("project_issues.$.project_issue_is_updated", true);

        mongoTemplate.updateFirst(query, update, Project.class);
    }

    public long countTotalProjectIssues(ObjectId projectId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("_id").is(projectId)),
                Aggregation.project().and(ArrayOperators.Size.lengthOfArray(
                        ConditionalOperators.ifNull("project_issues").then(Collections.EMPTY_LIST)
                )).as("count")
        );

        AggregationResults<ProjectIssuesCount> results = mongoTemplate.aggregate(aggregation, "projects", ProjectIssuesCount.class);
        if (results.getMappedResults().isEmpty()) {
            return 0L;
        }
        return results.getMappedResults().get(0).count();
    }

    private VuerdDoc getSampleVuerdDoc() {
        String json = """
                {
                  "canvas": {
                    "version": "2.2.13",
                    "width": 2000,
                    "height": 2000,
                    "scrollTop": -1,
                    "scrollLeft": 0,
                    "zoomLevel": 1,
                    "show": {
                      "tableComment": true,
                      "columnComment": true,
                      "columnDataType": true,
                      "columnDefault": true,
                      "columnAutoIncrement": false,
                      "columnPrimaryKey": true,
                      "columnUnique": false,
                      "columnNotNull": true,
                      "relationship": true
                    },
                    "database": "MySQL",
                    "databaseName": "",
                    "canvasType": "ERD",
                    "language": "GraphQL",
                    "tableCase": "pascalCase",
                    "columnCase": "camelCase",
                    "highlightTheme": "VS2015",
                    "bracketType": "none",
                    "setting": {
                      "relationshipDataTypeSync": true,
                      "relationshipOptimization": false,
                      "columnOrder": [
                        "columnName",
                        "columnDataType",
                        "columnNotNull",
                        "columnUnique",
                        "columnAutoIncrement",
                        "columnDefault",
                        "columnComment"
                      ]
                    },
                    "pluginSerializationMap": {}
                  },
                  "table": {
                    "tables": [],
                    "indexes": []
                  },
                  "memo": {
                    "memos": []
                  },
                  "relationship": {
                    "relationships": []
                  }
                }
                """;
        return gson.fromJson(json, VuerdDoc.class);
    }
}
