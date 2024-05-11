package io.ssafy.soupapi.domain.project.usecase.api;

import io.ssafy.soupapi.domain.project.usecase.dto.request.UpdateProjectImage;
import io.ssafy.soupapi.domain.project.usecase.application.ProjectUsecase;
import io.ssafy.soupapi.domain.project.usecase.dto.CreateAiProposal;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
     * @param userSecurityDTO member who create project
     * @return mongodb project objectId
     */
    @Operation(summary = "프로젝트 생성 요청")
    @PostMapping("")
    public ResponseEntity<BaseResponse<String>> createProject(
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.INSERT_SUCCESS,
                projectUsecase.createProject(userSecurityDTO)
        );
    }

    @Operation(summary = "AI 기획서 생성 요청")
    @PostMapping("/{projectId}/plan/ai")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<CreateAiProposal>> createAiProposal(
            @PathVariable(name = "projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO,
            @RequestBody CreateAiProposal createAiProposal
    ) {
        CreateAiProposal response = projectUsecase.createAiProposal(createAiProposal);

        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                response
        );
    }


    @Operation(summary = "프로젝트 사진 업데이트")
    @PatchMapping("/{projectId}/image")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<String>> changeProjectImage(
            @PathVariable String projectId,
            @RequestBody UpdateProjectImage updateProjectImage,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                projectUsecase.changeProjectImage(projectId, updateProjectImage)
        );
    }
}
