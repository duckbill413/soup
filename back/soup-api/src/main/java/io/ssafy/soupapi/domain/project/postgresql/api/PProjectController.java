package io.ssafy.soupapi.domain.project.postgresql.api;

import io.ssafy.soupapi.domain.project.postgresql.application.PProjectService;
import io.ssafy.soupapi.domain.project.postgresql.dto.response.SimpleProjectDto;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import io.ssafy.soupapi.global.security.TemporalMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param member     login member
     * @return simple project list with page info
     */
    @Operation(summary = "간단한 프로젝트 정보 리스트 조회")
    @GetMapping("")
    public ResponseEntity<BaseResponse<PageOffsetResponse<List<SimpleProjectDto>>>> findSimpleProjects(
            @Valid PageOffsetRequest pageOffset,
            @AuthenticationPrincipal TemporalMember member // TODO: member security 적용
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                pProjectService.findSimpleProjects(pageOffset, member) // TODO: member security 적용
        );
    }
}
