package io.ssafy.soupapi.domain.jira.api;

import io.ssafy.soupapi.domain.jira.application.JiraService;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.TemporalMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/jira")
@RequiredArgsConstructor
@Tag(name = "JIRA", description = "지라 API 연동 Controller")
public class JiraController {
    private final JiraService jiraService;

    @Operation(summary = "지라 프로젝트 유저 검색")
    @GetMapping("/{projectId}")
    public ResponseEntity<BaseResponse<List<JiraUserDatum>>> findJiraTeamMembers(
            @PathVariable("projectId") String projectId,
            @AuthenticationPrincipal TemporalMember member
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                jiraService.findJiraTeamInfo(projectId)
        );
    }
}
