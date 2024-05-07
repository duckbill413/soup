package io.ssafy.soupapi.domain.projectbuilder.api;

import io.ssafy.soupapi.domain.projectbuilder.application.ProjectBuilderService;
import io.ssafy.soupapi.domain.projectbuilder.dto.request.ChangeProjectBuilderInfo;
import io.ssafy.soupapi.domain.projectbuilder.dto.response.GetProjectBuilderInfo;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
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
public class ProjectBuilderController {
    private final ProjectBuilderService projectBuilderService;

    @PostMapping("/{projectId}/builder")
    @PreAuthorize("!@authService.hasViewerProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public void buildProject(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        projectBuilderService.buildProject(projectId);
    }

    @PutMapping("/{projectId}/builder")
    @PreAuthorize("!@authService.hasViewerProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<GetProjectBuilderInfo>> changeProjectBuilderInfo(
            @PathVariable String projectId,
            @Valid @RequestBody ChangeProjectBuilderInfo changeProjectBuilderInfo,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                projectBuilderService.changeBuilderInfo(projectId, changeProjectBuilderInfo)
        );
    }

    @GetMapping("/{projectId}/builder")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<GetProjectBuilderInfo>> findProjectBuilderInfo(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                projectBuilderService.findBuilderInfo(projectId)
        );
    }
}
