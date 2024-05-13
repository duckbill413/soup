package io.ssafy.soupapi.domain.projectauth.api;

import io.ssafy.soupapi.domain.projectauth.application.ProjectAuthService;
import io.ssafy.soupapi.domain.projectauth.dto.response.GetProjectAccessInfo;
import io.ssafy.soupapi.domain.projectauth.dto.response.GetProjectTeamMember;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "프로젝트 팀", description = "ProjectAuth Domain Controller")
public class ProjectAuthController {
    private final ProjectAuthService projectAuthService;

    @Operation(summary = "팀원 정보 요청", description = "특정 프로젝트에 참여한 팀원들의 정보와 권한 요청")
    @GetMapping("/{projectId}/teams")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<List<GetProjectTeamMember>>> findProjectTeamMembers(
            @PathVariable(name = "projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                projectAuthService.findProjectTeamMembers(projectId)
        );
    }

    @Operation(summary = "프로젝트 접근 권한 확인", description = "프로젝트 접근 권한 확인",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로젝트 권한 확인됨"),
                    @ApiResponse(responseCode = "404", description = "프로젝트 권한 없음")
            })
    @GetMapping("/{projectId}/check")
    public ResponseEntity<BaseResponse<GetProjectAccessInfo>> checkProjectAccessInfo(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                projectAuthService.checkProjectAccessInfo(projectId, userSecurityDTO)
        );
    }
}
