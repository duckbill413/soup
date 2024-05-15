package io.ssafy.soupapi.usecase.api;

import io.ssafy.soupapi.domain.project.mongodb.application.MProjectService;
import io.ssafy.soupapi.domain.projectbuilder.application.ProjectBuilderService;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.ssafy.soupapi.usecase.application.UpdateProjectInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "프로젝트 빌더", description = "프로젝트 빌드 API Controller")
public class BuildProjectController {
    private final UpdateProjectInfoService updateProjectInfoService;
    private final MProjectService mProjectService;
    private final ProjectBuilderService projectBuilderService;

    @Operation(summary = "프로젝트 빌드")
    @PostMapping("/{projectId}/builder")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<String>> buildProject(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        updateProjectInfoService.liveUpdateProjectInfo(projectId);
        mProjectService.liveProjectVuerd(new ObjectId(projectId));
        projectBuilderService.liveChangeBuilderInfo(projectId);
        return BaseResponse.success(
                SuccessCode.INSERT_SUCCESS,
                projectBuilderService.buildProject(projectId)
        );
    }
}
