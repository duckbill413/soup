package io.ssafy.soupapi.domain.jira.api;

import io.ssafy.soupapi.domain.jira.application.JiraService;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.domain.jira.dto.response.JiraIssue;
import io.ssafy.soupapi.domain.jira.dto.response.GetJiraIssueType;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "JIRA", description = "지라 API 연동 Controller")
public class JiraController {
    private final JiraService jiraService;

    @Operation(summary = "지라 프로젝트 유저 검색")
    @GetMapping("/{projectId}/jira")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<List<JiraUserDatum>>> findJiraTeamMembers(
            @PathVariable("projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
            ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                jiraService.findJiraTeamInfo(projectId)
        );
    }

    @Operation(summary = "지라 프로젝트 동기화")
    @PostMapping("/{projectId}/jira")
    @PreAuthorize("@authService.hasPrimaryProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<String>> syncJiraProjectByIssues(
            @PathVariable("projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                jiraService.syncJiraProjectByIssues(new ObjectId(projectId))
        );
    }

    @Operation(summary = "지라 프로젝트 이슈 목록 조회")
    @GetMapping("/{projectId}/jira/issues")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<PageOffsetResponse<List<JiraIssue>>>> findJiraIssues(
            @PathVariable("projectId") String projectId,
            PageOffsetRequest pageOffsetRequest,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                jiraService.findJiraIssues(projectId, pageOffsetRequest)
        );
    }

    @Operation(summary = "지라 프로젝트 사용 가능 Issue types 조회")
    @GetMapping("/{projectId}/jira/issues/types")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<List<GetJiraIssueType>>> findJiraIssueTypes(
            @PathVariable("projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                jiraService.findJiraIssueTypes(projectId)
        );
    }
}
