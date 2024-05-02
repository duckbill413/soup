package io.ssafy.soupapi.domain.project.mongodb.application;

import io.ssafy.soupapi.domain.project.mongodb.dao.MProjectRepository;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectProposal;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectTool;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectProposal;
import io.ssafy.soupapi.domain.project.mongodb.entity.Info;
import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.ProjectIssue;
import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.OffsetPagination;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.security.TemporalMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class MProjectServiceImpl implements MProjectService {
    private final MProjectRepository mProjectRepository;
    private final MongoTemplate mongoTemplate;

    /**
     * 프로젝트 생성 및 최초 팀 구성 설정
     * 최초 생성자 권한을 ADMIN으로 지정
     *
     * @param createProjectDto new project's project data
     * @param member           project maker
     * @return mongodb project object id
     */
    @Transactional
    @Override
    public ObjectId createProject(CreateProjectDto createProjectDto, TemporalMember temporalMember) { // TODO: member security 적용
        // 프로젝트 및 프로젝트 이름 설정
        var project = Project.builder()
                .info(
                        Info.builder()
                                .name(createProjectDto.name())
                                .imgUrl(createProjectDto.imgUrl())
                                .startDate(createProjectDto.getStartDate())
                                .endDate(createProjectDto.getEndDate())
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

        var issueCount = Optional.ofNullable(mProjectRepository.findProjectIssuesCount(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.FAILED_TO_UPDATE_PROJECT)).getIssues()).orElse(List.of()).size();
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
    public PageOffsetResponse<List<ProjectIssue>> updateProjectIssues(ObjectId projectId, List<ProjectIssue> issues, PageOffsetRequest pageOffsetRequest, TemporalMember member) {
        for (ProjectIssue issue : issues) {
            // 변경 사항이 없는 경우
            if (!issue.isUpdated()) {
                continue;
            }

            // 새로 저장되는 데이터의 경우
            if (Objects.isNull(issue.getProjectIssueId())) {
                issue.setProjectIssueId(UUID.randomUUID());
                issue.setCreated(true);
                continue;
            }

            // 업데이트 되는 데이터의 경우
            updateProjectIssue(projectId, issue);
        }
        return findProjectIssues(projectId, pageOffsetRequest);
    }

    public void insertProjectIssue(ObjectId projectId, ProjectIssue projectIssue) {
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
        AggregationResults<Document> results = mongoTemplate.aggregate(
                Aggregation.newAggregation(
                        Aggregation.match(Criteria.where("_id").is(projectId)),
                        Aggregation.project()
                                .and(
                                        ConditionalOperators.when(Criteria.where("project_issues").exists(true))
                                                .then(ArrayOperators.Size.lengthOfArray("$project_issues"))
                                                .otherwise(0)
                                ).as("issueCount")
                ),
                "projects",
                Document.class
        );

        Document result = results.getUniqueMappedResult();
        return result != null ? result.getInteger("issueCount", 0) : 0;
    }
}
