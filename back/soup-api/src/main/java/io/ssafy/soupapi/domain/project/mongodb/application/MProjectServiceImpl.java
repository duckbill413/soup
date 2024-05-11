package io.ssafy.soupapi.domain.project.mongodb.application;

import com.google.gson.Gson;
import io.ssafy.soupapi.domain.project.mongodb.dao.MProjectRepository;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.*;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.*;
import io.ssafy.soupapi.domain.project.mongodb.entity.Info;
import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.entity.issue.ProjectIssue;
import io.ssafy.soupapi.domain.project.usecase.dto.request.UpdateProjectImage;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.OffsetPagination;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.ssafy.soupapi.global.util.StringParserUtil;
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
import java.util.*;

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

    /**
     * 프로젝트 이슈 리스트 조회
     * Offset 기반 페이지네이션 적용
     *
     * @param projectId         이슈를 조회할 프로젝트 ID
     * @param pageOffsetRequest Offset Paging 정보
     * @return 이슈 리스트
     */
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

    /**
     * 프로젝트 이슈 정보 업데이트
     *
     * @param projectId         이슈를 업데이트할 프로젝트 ID
     * @param issues            업데이트할 이슈 정보 리스트
     * @param pageOffsetRequest Offset Paging 정보
     * @return 이슈 리스트
     */
    @Override
    public PageOffsetResponse<List<ProjectIssue>> updateProjectIssues(ObjectId projectId, List<ProjectIssue> issues, PageOffsetRequest pageOffsetRequest) {
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

    /**
     * 프로젝트 ERD 정보
     *
     * @param projectId ERD를 조회할 프로젝트 ID
     * @return ERD 정보
     */
    @Override
    public Object findProjectVuerd(ObjectId projectId) {
        var project = mProjectRepository.findVuerdById(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));
        if (Objects.isNull(project.getVuerd())) {
            Object sampleVuerd = getSampleVuerdDoc();
            mProjectRepository.changeVuerdById(projectId, sampleVuerd);
            return sampleVuerd;
        }

        return project.getVuerd();
    }

    /**
     * 프로젝트 ERD 업데이트
     *
     * @param projectId ERD를 업데이트할 프로젝트 ID
     * @param vuerdDoc  업데이트 VUERD 정보
     * @return 프로젝트 ERD 정보
     */
    @Transactional
    @Override
    public Object changeProjectVuerd(ObjectId projectId, Object vuerdDoc) {
        var project = mProjectRepository.findVuerdById(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));

        mProjectRepository.changeVuerdById(projectId, vuerdDoc);
        return vuerdDoc;
    }

    /**
     * 간단한 프로젝트 이슈 리스트 정보
     *
     * @param projectId 이슈 리스트를 조회할 프로젝트 ID
     * @return 간단한 프로젝트 이슈 리스트
     */
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

    /**
     * 사용 가능한 PathVariable 이름 리스트
     *
     * @param projectId 프로젝트 ID
     * @param apiDocId  프로젝트 API DOC ID
     * @return API DOC에서 사용 가능한 PathVariable 이름 리스트
     */
    @Override
    public List<String> findProjectValidPathVariableNames(ObjectId projectId, UUID apiDocId) {
        ApiDoc apiDoc = getProjectApiDoc(projectId, apiDocId);
        if (Objects.isNull(apiDoc.getApiUriPath())) {
            return List.of();
        }

        return new ArrayList<>(StringParserUtil.extractBracketsContent(apiDoc.getApiUriPath()));
    }

    /**
     * API DOC 조회
     * - API DOC에서 사용 가능한 PathVariable 리스트를 함께 조회
     *
     * @param projectId 프로젝트 ID
     * @param apiDocId  API DOC ID
     * @return 프로젝트 API DOC 정보
     */
    @Override
    public GetApiDoc findProjectSingleApiDocs(ObjectId projectId, UUID apiDocId) {
        ApiDoc apiDoc = getProjectApiDoc(projectId, apiDocId);

        return GetApiDoc.of(apiDoc, new ArrayList<>(StringParserUtil.extractBracketsContent(apiDoc.getApiUriPath())));
    }

    /**
     * API DOC 조회
     *
     * @param projectId 프로젝트 ID
     * @param apiDocId  API DOC ID
     * @return 프로젝트 API DOC 정보
     */
    private ApiDoc getProjectApiDoc(ObjectId projectId, UUID apiDocId) {
        Query query = new Query().addCriteria(Criteria.where("_id").is(projectId));
        query.fields().include("project_api_doc.api_docs.$");
        query.addCriteria(Criteria.where("project_api_doc.api_docs.api_doc_id").is(apiDocId));

        Project project = mongoTemplate.findOne(query, Project.class);

        if (project == null || project.getApiDocs() == null || project.getApiDocs().getApiDocList().isEmpty()) {
            throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_API_DOC);
        }

        return project.getApiDocs().getApiDocList().get(0);
    }

    /**
     * 사용 가능한 Domain 이름 목록
     * ERD를 기준으로 Domain 리스트 조회
     *
     * @param projectId 프로젝트 ID
     * @return ERD 기반 Domain 리스트 조회
     */
    @Override
    public List<String> findProjectValidDomainNames(ObjectId projectId) {
        var project = mProjectRepository.findVuerdById(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));

        if (Objects.isNull(project.getVuerd())) {
            return List.of();
        }

        return getProjectDomainsFromERD(project);
    }

    /**
     * 프로젝트 API DOC 정보 업데이트
     *
     * @param projectId    프로젝트 ID
     * @param updateApiDoc 업데이트할 API DOC 정보
     * @return 업데이트를 완료한 API DOC ID
     */
    @Override
    public String updateProjectApiDoc(String projectId, UpdateApiDoc updateApiDoc) {
        ApiDoc apiDoc = UpdateApiDoc.toApiDoc(updateApiDoc);
        Query query = new Query(Criteria.where("_id").is(new ObjectId(projectId)));

        // id가 없으면 save 수행
        if (Objects.isNull(apiDoc.getId())) {
            apiDoc.setId(UUID.randomUUID()); // id setting
            Update update = new Update().addToSet("project_api_doc.api_docs", apiDoc);
            mongoTemplate.updateFirst(query, update, Project.class);
            return apiDoc.getId().toString();
        }
        // update 수행
        query.addCriteria(Criteria.where("project_api_doc.api_docs.api_doc_id").is(apiDoc.getId()));
        Update update = new Update().set("project_api_doc.api_docs.$", apiDoc);
        mongoTemplate.updateFirst(query, update, Project.class);
        return updateApiDoc.id().toString();
    }

    @Override
    public void changeProjectImage(ObjectId projectId, UpdateProjectImage updateProjectImage) {
        try {
            Query query = new Query(Criteria.where("_id").is(projectId));
            Update update = new Update().set("project_info.project_img_url", updateProjectImage.imgUrl());
            mongoTemplate.updateFirst(query, update, Project.class);
        } catch (Exception e) {
            throw new BaseExceptionHandler(ErrorCode.FAILED_TO_CHANGE_PROJECT_IMAGE);
        }
    }

    /**
     * 프로젝트 도메인 리스트 정보
     *
     * @param project 프로젝트 ID
     * @return 프로젝트 도메인 리스트
     */
    private List<String> getProjectDomainsFromERD(Project project) {
        var vuerdObj = project.getVuerd();
        var domains = new ArrayList<String>();
        if (vuerdObj instanceof LinkedHashMap<?, ?>) {
            vuerdObj = ((LinkedHashMap<?, ?>) vuerdObj).get("$set");

            if (vuerdObj instanceof LinkedHashMap<?, ?>) {
                var doc = ((LinkedHashMap<?, ?>) vuerdObj).get("doc");
                var collections = ((LinkedHashMap<?, ?>) vuerdObj).get("collections");

                if (doc instanceof LinkedHashMap<?, ?>) {
                    var tableIdsObj = ((LinkedHashMap<?, ?>) doc).get("tableIds");

                    List<String> tableIds = new ArrayList<>();
                    if (tableIdsObj instanceof List<?>) {
                        ((List<?>) tableIdsObj).forEach(tableId -> tableIds.add(tableId.toString()));
                    }

                    if (collections instanceof LinkedHashMap<?, ?>) {
                        var tableEntities = ((LinkedHashMap<?, ?>) collections).get("tableEntities");

                        if (tableEntities instanceof LinkedHashMap<?, ?>) {

                            for (String tableId : tableIds) {
                                var table = ((LinkedHashMap<?, ?>) tableEntities).get(tableId);
                                if (table instanceof LinkedHashMap<?, ?>) {
                                    domains.add((((LinkedHashMap<?, ?>) table).get("name")).toString().toLowerCase());
                                }
                            }
                        }
                    }
                }
            }
        }

        return domains;
    }

    /**
     * 프로젝트 이슈 삽입
     *
     * @param projectId    프로젝트 ID
     * @param projectIssue 추가할 프로젝트 이슈 정보
     */
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

    /**
     * 프로젝트 이슈 개수 조회
     *
     * @param projectId 프로젝트 ID
     * @return 프로젝트 이슈 개수
     */
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

    /**
     * Vuerd 기본 Schema 정보
     *
     * @return Vuerd Default Schema
     */
    private Object getSampleVuerdDoc() {
        String json = """
                {
                   "$schema": "https://raw.githubusercontent.com/dineug/erd-editor/main/json-schema/schema.json",
                   "version": "3.0.0",
                   "settings": {
                     "width": 2000,
                     "height": 2000,
                     "scrollTop": 0,
                     "scrollLeft": 0,
                     "zoomLevel": 1,
                     "show": 431,
                     "database": 4,
                     "databaseName": "",
                     "canvasType": "ERD",
                     "language": 1,
                     "tableNameCase": 4,
                     "columnNameCase": 2,
                     "bracketType": 1,
                     "relationshipDataTypeSync": true,
                     "relationshipOptimization": false,
                     "columnOrder": [
                       1,
                       2,
                       4,
                       8,
                       16,
                       32,
                       64
                     ],
                     "maxWidthComment": -1,
                     "ignoreSaveSettings": 0
                   },
                   "doc": {
                     "tableIds": [],
                     "relationshipIds": [],
                     "indexIds": [],
                     "memoIds": []
                   },
                   "collections": {
                     "tableEntities": {},
                     "tableColumnEntities": {},
                     "relationshipEntities": {},
                     "indexEntities": {},
                     "indexColumnEntities": {},
                     "memoEntities": {}
                   },
                   "lww": {}
                }
                """;
        return gson.fromJson(json, Object.class);
    }
}
