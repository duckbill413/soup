package io.ssafy.soupapi.domain.jira.api;

import io.ssafy.soupapi.domain.jira.application.JiraService;
import io.ssafy.soupapi.domain.jira.dto.Issue;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.TemporalMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #member.getId())")
    public ResponseEntity<BaseResponse<List<JiraUserDatum>>> findJiraTeamMembers(
            @PathVariable("projectId") String projectId,
            @AuthenticationPrincipal TemporalMember member
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                jiraService.findJiraTeamInfo(projectId)
        );
    }

    @Operation(summary = "지라 프로젝트 동기화")
    @PostMapping("/{projectId}/jira")
    @PreAuthorize("@authService.hasPrimaryProjectRoleMember(#projectId, #member.getId())")
    public ResponseEntity<BaseResponse<String>> syncJiraProjectByIssues(
            @PathVariable("projectId") String projectId,
            @AuthenticationPrincipal TemporalMember member
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                jiraService.syncJiraProjectByIssues(projectId)
        );
    }

    @Operation(summary = "지라 프로젝트 이슈 목록 조회")
    @GetMapping("/{projectId}/jira/issues")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #member.getId())")
    public ResponseEntity<BaseResponse<List<Issue>>> findJiraIssues(
            @PathVariable("projectId") String projectId,
            @AuthenticationPrincipal TemporalMember member
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                jiraService.findJiraIssues(projectId)
        );
    }
}
