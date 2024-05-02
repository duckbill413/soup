package io.ssafy.soupapi.usecase.api;

import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.ssafy.soupapi.usecase.application.ParticipateTeamService;
import io.ssafy.soupapi.usecase.dto.request.ParticipateTeam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "프로젝트 팀", description = "팀원 참가 Usecase Controller")
public class ParticipateTeamController {
    private final ParticipateTeamService participateTeamService;

    @Operation(summary = "프로젝트 팀원 참가")
    @PostMapping("/api/projects/participation")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BaseResponse<String>> participateProject(
            @RequestBody ParticipateTeam participateTeam,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.INSERT_SUCCESS,
                participateTeamService.participateToTeam(participateTeam, userSecurityDTO)
        );
    }
}
