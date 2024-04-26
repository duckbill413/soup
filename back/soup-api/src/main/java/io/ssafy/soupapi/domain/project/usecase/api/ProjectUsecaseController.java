package io.ssafy.soupapi.domain.project.usecase.api;

import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectProposal;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectProposal;
import io.ssafy.soupapi.domain.project.usecase.application.ProjectUsecase;
import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.TemporalMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "프로젝트", description = "Project Domain Usecase Controller")
public class ProjectUsecaseController {
    private final ProjectUsecase projectUsecase;

    /**
     * 프로젝트 생성 Post API
     *
     * @param createProjectDto request dto for create project
     * @param member           member who create project
     * @return mongodb project objectId
     */
    @Operation(summary = "프로젝트 생성 요청")
    @PostMapping("")
    public ResponseEntity<BaseResponse<String>> createProject(
            @Valid @RequestBody CreateProjectDto createProjectDto,
            @AuthenticationPrincipal TemporalMember temporalMember
    ) {
        return BaseResponse.success(
                SuccessCode.INSERT_SUCCESS,
                projectUsecase.createProject(createProjectDto, temporalMember) // TODO: member security 적용
        );
    }

    /**
     * MongoDB Project Info 조회
     *
     * @param projectId 조회하는 Project의 Id
     * @param member    Project를 조회하는 대상
     * @return ProjectInfoDto Object
     */
    @Operation(summary = "프로젝트 정보 요청", description = "프로젝트 개요 화면의 프로젝트 정보 요청")
    @GetMapping("/{projectId}/info")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #member.getId())")
    public ResponseEntity<BaseResponse<GetProjectInfo>> findProjectInfo(
            @PathVariable(name = "projectId") String projectId,
            @AuthenticationPrincipal TemporalMember member // TODO: security member
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                projectUsecase.findProjectInfo(projectId, member)
        );
    }

    /**
     * 프로젝트 지라 키 정보 요청
     *
     * @param projectId 키 정보를 요청할 프로젝트 아이디
     * @param member    키 정보를 요청하는 멤버
     * @return 지라 유저 이름 및 키 정보
     */
    @Operation(summary = "프로젝트 지라 키 정보 요청", description = "프로젝트 Jira Key 정보 요청 (ADMIN, MAINTAINER)")
    @GetMapping("/{projectId}/info/jira")
    @PreAuthorize("@authService.hasPrimaryProjectRoleMember(#projectId, #member.getId())")
    public ResponseEntity<BaseResponse<GetProjectJiraKey>> findProjectJiraKey(
            @PathVariable(name = "projectId") String projectId,
            @AuthenticationPrincipal TemporalMember member
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                projectUsecase.findProjectJiraKey(projectId, member)
        );
    }

    /**
     * 프로젝트 지라 키 정보 업데이트
     *
     * @param projectId            업데이트할 프로젝트의 Id
     * @param updateProjectJiraKey 업데이트할 프로젝트 지라 정보
     * @param member               키 정보를 업데이트하는 멤버 정보
     * @return 업데이트 된 지라 키 정보
     */
    @Operation(summary = "프로젝트 지라 키 정보 수정", description = "프로젝트 Jira Key 정보 수정 (ADMIN, MAINTAINER)")
    @PutMapping("/{projectId}/info/jira")
    @PreAuthorize("@authService.hasPrimaryProjectRoleMember(#projectId, #member.getId())")
    public ResponseEntity<BaseResponse<GetProjectJiraKey>> updateProjectJiraKey(
            @PathVariable(name = "projectId") String projectId,
            @Valid @RequestBody UpdateProjectJiraKey updateProjectJiraKey,
            @AuthenticationPrincipal TemporalMember member
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                projectUsecase.updateProjectJiraKey(projectId, updateProjectJiraKey, member)
        );
    }

    /**
     * 프로젝트 정보(개요 페이지) 수정
     * - 키, 프로젝트 이미지, 팀원 정보는 수정 안됨
     *
     * @param projectId
     * @param updateProjectInfo
     * @param member
     * @return
     */

    @Operation(summary = "프로젝트 정보 수정", description = "프로젝트 개요 화면 정보 수정 <br>Jira Key, 프로젝트 이미지, 팀원 정보는 수정 불가")
    @PutMapping("/{projectId}/info")
    @PreAuthorize("!@authService.hasViewerProjectRoleMember(#projectId, #member.getId())")
    public ResponseEntity<BaseResponse<GetProjectInfo>> updateProjectInfo(
            @PathVariable(name = "projectId") String projectId,
            @RequestBody UpdateProjectInfo updateProjectInfo,
            @AuthenticationPrincipal TemporalMember member
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                projectUsecase.updateProjectInfo(projectId, updateProjectInfo, member)
        );
    }

    /**
     * 프로젝트 제안서 업데이트
     *
     * @param projectId             업데이트하는 프로젝트의 Id
     * @param updateProjectProposal 업데이트하는 제안서 정보
     * @param member                업데이트하는 멤버 정보
     * @return 업데이트 완료된 프로젝트 제안서 정보
     */
    @Operation(summary = "프로젝트 제안서 업데이트", description = "프로젝트 기획서 정보 업데이트")
    @PutMapping("/{projectId}/proposal")
    @PreAuthorize("!@authService.hasViewerProjectRoleMember(#projectId, #member.getId())")
    public ResponseEntity<BaseResponse<GetProjectProposal>> changeProjectProposal(
            @PathVariable(name = "projectId") String projectId,
            @Valid @RequestBody UpdateProjectProposal updateProjectProposal,
            @AuthenticationPrincipal TemporalMember member // TODO: security member
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                projectUsecase.updateProjectProposal(projectId, updateProjectProposal, member)
        );
    }

    /**
     * 프로젝트 제안서 정보 조회
     *
     * @param projectId 제안서를 조회할 프로젝트 Id
     * @param member    제안서를 조회하는 멤버
     * @return 프로젝트 제안서 정보
     */
    @Operation(summary = "프로젝트 제안서 조회")
    @GetMapping("/{projectId}/proposal")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #member.getId())")
    public ResponseEntity<BaseResponse<GetProjectProposal>> findProjectProposal(
            @PathVariable(name = "projectId") String projectId,
            @AuthenticationPrincipal TemporalMember member // TODO: security member
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                projectUsecase.findProjectProposal(projectId, member)
        );
    }
}
