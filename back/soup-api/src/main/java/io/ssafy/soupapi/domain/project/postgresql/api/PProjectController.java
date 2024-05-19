package io.ssafy.soupapi.domain.project.postgresql.api;

import io.ssafy.soupapi.domain.project.postgresql.application.PProjectService;
import io.ssafy.soupapi.domain.project.postgresql.dto.response.SimpleProjectDto;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "프로젝트", description = "PostgreSQL Project Domain Controller")
@Log4j2
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class PProjectController {
    private final PProjectService pProjectService;

    /**
     * 간단한 프로젝트 정보 리스트 조회
     *
     * @param pageOffset offset page request
     * @param userSecurityDTO     login member
     * @return simple project list with page info
     */
    @Operation(summary = "간단한 프로젝트 정보 리스트 조회")
    @GetMapping("")
    public ResponseEntity<BaseResponse<PageOffsetResponse<List<SimpleProjectDto>>>> findSimpleProjects(
            @Valid PageOffsetRequest pageOffset,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                pProjectService.findSimpleProjects(pageOffset, userSecurityDTO)
        );
    }

    @Operation(summary = "프로젝트 삭제 API", description = "프로젝트 관리자만 삭제 가능")
    @DeleteMapping("/{projectId}")
    @PreAuthorize("@authService.hasPrimaryProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<String>> deleteProject(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.DELETE_SUCCESS,
                pProjectService.deleteProject(projectId)
        );
    }
}
