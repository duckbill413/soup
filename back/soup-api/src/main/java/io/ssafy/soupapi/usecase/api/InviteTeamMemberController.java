package io.ssafy.soupapi.usecase.api;

import io.ssafy.soupapi.domain.project.usecase.dto.request.InviteTeamMember;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.ssafy.soupapi.usecase.application.InviteTeamMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "프로젝트 팀", description = "팀원 초대 Usecase Controller")
public class InviteTeamMemberController {
    private final InviteTeamMemberService inviteTeamMemberService;

    @Operation(summary = "프로젝트 팀원 초대")
    @PostMapping("/api/projects/{projectId}/teams")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<String>> inviteTeamMember(
            @PathVariable(name = "projectId") String projectId,
            @RequestBody InviteTeamMember inviteTeamMember,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
            ) {
        return BaseResponse.success(
                SuccessCode.INSERT_SUCCESS,
                inviteTeamMemberService.inviteTeamMember(projectId, inviteTeamMember, userSecurityDTO)
        );
    }
}