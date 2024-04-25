package io.ssafy.soupapi.domain.project.usecase.api;

import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectProposal;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectProposal;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.ProjectInfoDto;
import io.ssafy.soupapi.domain.project.usecase.application.ProjectUsecase;
import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.domain.project.usecase.dto.request.InviteTeammate;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.TemporalMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BaseResponse<ProjectInfoDto>> findProjectInfo(
            @PathVariable(name = "projectId") String projectId,
            @AuthenticationPrincipal TemporalMember member // TODO: security member
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                projectUsecase.findProjectInfo(new ObjectId(projectId), member)
        );
    }

    @Operation(description = "프로젝트 제안서 업데이트")
    @PutMapping("/{projectId}/proposal")
    public ResponseEntity<BaseResponse<GetProjectProposal>> changeProjectProposal(
            @Valid @RequestBody UpdateProjectProposal updateProjectProposal,
            @AuthenticationPrincipal TemporalMember member // TODO: security member
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                projectUsecase.updateProjectProposal(updateProjectProposal, member)
        );
    }

    @Operation(description = "프로젝트 제안서 조회")
    @GetMapping("/{projectId}/proposal")
    public ResponseEntity<BaseResponse<GetProjectProposal>> findProjectProposal(
            @PathVariable(name = "projectId") String projectId,
            @AuthenticationPrincipal TemporalMember member // TODO: security member
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                projectUsecase.findProjectProposal(new ObjectId(projectId), member)
        );
    }

    @Operation(description = "프로젝트 팀원 초대")
    @PostMapping("/{projectId}/team")
    public ResponseEntity<BaseResponse<String>> inviteProjectTeammate(
            @RequestBody InviteTeammate inviteTeammate,
            @AuthenticationPrincipal TemporalMember member // TODO: security member
    ) throws MessagingException {
        return BaseResponse.success(
                SuccessCode.INSERT_SUCCESS,
                projectUsecase.inviteProjectTeammate(inviteTeammate, member)
        );
    }
}
