package io.ssafy.soupapi.domain.project.mongodb.api;

import io.ssafy.soupapi.domain.project.mongodb.application.MProjectService;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateApiDoc;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectProposal;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.*;
import io.ssafy.soupapi.domain.project.mongodb.entity.issue.ProjectIssue;
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
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "프로젝트", description = "Project Domain MongoDB Controller")
public class MProjectController {
    private final MProjectService mProjectService;

    /**
     * 프로젝트 정보(개요 페이지) 조회
     *
     * @param projectId       조회하는 Project의 Id
     * @param userSecurityDTO Project를 조회하는 대상
     * @return ProjectInfoDto Object
     */
    @Operation(summary = "프로젝트 정보 요청", description = "프로젝트 개요 화면의 프로젝트 정보 요청")
    @GetMapping("/{projectId}/info")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<GetProjectInfo>> findProjectInfo(
            @PathVariable(name = "projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                mProjectService.findProjectInfoAndTools(new ObjectId(projectId))
        );
    }

    /**
     * 프로젝트 제안서 정보 조회
     *
     * @param projectId       제안서를 조회할 프로젝트 Id
     * @param userSecurityDTO 제안서를 조회하는 멤버
     * @return 프로젝트 제안서 정보
     */
    @Operation(summary = "프로젝트 제안서 조회")
    @GetMapping("/{projectId}/proposal")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<GetProjectProposal>> findProjectProposal(
            @PathVariable(name = "projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                mProjectService.findProjectProposal(new ObjectId(projectId))
        );
    }

    /**
     * 프로젝트 제안서 업데이트
     *
     * @param projectId             업데이트하는 프로젝트의 Id
     * @param updateProjectProposal 업데이트하는 제안서 정보
     * @param userSecurityDTO       업데이트하는 멤버 정보
     * @return 업데이트 완료된 프로젝트 제안서 정보
     */
    @Operation(summary = "프로젝트 제안서 업데이트", description = "프로젝트 기획서 정보 업데이트")
    @PutMapping("/{projectId}/proposal")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<GetProjectProposal>> changeProjectProposal(
            @PathVariable(name = "projectId") String projectId,
            @Valid @RequestBody UpdateProjectProposal updateProjectProposal,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                mProjectService.updateProjectProposal(new ObjectId(projectId), updateProjectProposal)
        );
    }

    /**
     * 프로젝트 제안서 Liveblocks 연동
     *
     * @param projectId             업데이트하는 프로젝트의 Id
     * @param userSecurityDTO       업데이트하는 멤버 정보
     * @return 업데이트 완료된 프로젝트 제안서 정보
     */
    @Operation(summary = "Liveblocks 프로젝트 제안서 업데이트", description = "Liveblocks 프로젝트 기획서 정보 연동")
    @PostMapping("/{projectId}/proposal/live")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<GetProjectProposal>> liveUpdateProjectProposal(
            @PathVariable(name = "projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                mProjectService.liveUpdateProjectProposal(new ObjectId(projectId))
        );
    }

    /**
     * 프로젝트 지라 키 정보 요청
     *
     * @param projectId       키 정보를 요청할 프로젝트 아이디
     * @param userSecurityDTO 키 정보를 요청하는 멤버
     * @return 지라 유저 이름 및 키 정보
     */
    @Operation(summary = "프로젝트 지라 키 정보 요청", description = "프로젝트 Jira Key 정보 요청 (ADMIN, MAINTAINER)")
    @GetMapping("/{projectId}/info/jira")
    @PreAuthorize("@authService.hasPrimaryProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<GetProjectJiraKey>> findProjectJiraKey(
            @PathVariable(name = "projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                mProjectService.findProjectJiraKey(new ObjectId(projectId))
        );
    }

    /**
     * 프로젝트 지라 키 정보 업데이트
     *
     * @param projectId            업데이트할 프로젝트의 Id
     * @param updateProjectJiraKey 업데이트할 프로젝트 지라 정보
     * @param userSecurityDTO      키 정보를 업데이트하는 멤버 정보
     * @return 업데이트 된 지라 키 정보
     */
    @Operation(summary = "프로젝트 지라 키 정보 수정", description = "프로젝트 Jira Key 정보 수정 (ADMIN, MAINTAINER)")
    @PutMapping("/{projectId}/info/jira")
    @PreAuthorize("@authService.hasPrimaryProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<GetProjectJiraKey>> updateProjectJiraKey(
            @PathVariable(name = "projectId") String projectId,
            @Valid @RequestBody UpdateProjectJiraKey updateProjectJiraKey,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                mProjectService.updateProjectJiraKey(new ObjectId(projectId), updateProjectJiraKey)
        );
    }

    @Operation(summary = "프로젝트 이슈 목록 조회")
    @GetMapping("/{projectId}/issues")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<PageOffsetResponse<List<ProjectIssue>>>> findProjectIssues(
            @PathVariable(name = "projectId") String projectId,
            PageOffsetRequest pageOffsetRequest,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                mProjectService.findProjectIssues(new ObjectId(projectId), pageOffsetRequest)
        );
    }

    @Operation(summary = "프로젝트 이슈 업데이트")
    @PutMapping("/{projectId}/issues")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<PageOffsetResponse<List<ProjectIssue>>>> updateProjectIssues(
            @PathVariable(name = "projectId") String projectId,
            @RequestBody List<ProjectIssue> issues,
            PageOffsetRequest pageOffsetRequest,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                mProjectService.updateProjectIssues(new ObjectId(projectId), issues, pageOffsetRequest)
        );
    }

    @Operation(summary = "프로젝트 ERD 조회")
    @GetMapping("/{projectId}/vuerd")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<Object>> findProjectVuerd(
            @PathVariable(name = "projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        mProjectService.liveProjectVuerd(new ObjectId(projectId));
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                mProjectService.findProjectVuerd(new ObjectId(projectId))
        );
    }

    @Operation(summary = "프로젝트 ERD 수정")
    @PutMapping("/{projectId}/vuerd")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<Object>> changeProjectVuerd(
            @PathVariable(name = "projectId") String projectId,
            @RequestBody Object vuerdDoc,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                mProjectService.changeProjectVuerd(new ObjectId(projectId), vuerdDoc)
        );
    }

    @Operation(summary = "Liveblocks 프로젝트 ERD 업데이트")
    @PostMapping("/{projectId}/vuerd/live")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<Object>> changeProjectVuerdWithLiveblocks(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                mProjectService.liveProjectVuerd(new ObjectId(projectId))
        );
    }

    @Operation(summary = "프로젝트 API Doc 등록/업데이트")
    @PostMapping("/{projectId}/api-docs")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<String>> insertProjectApiDoc(
            @PathVariable String projectId,
            @RequestBody UpdateApiDoc updateApiDoc,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.INSERT_SUCCESS,
                mProjectService.updateProjectApiDoc(projectId, updateApiDoc)
        );
    }

    @Operation(summary = "Liveblocks API DOC 업데이트")
    @PostMapping("/{projectId}/api-docs/live")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<List<GetSimpleApiDoc>>> liveProjectApiDoc(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                mProjectService.liveProjectApiDoc(new ObjectId(projectId))
        );
    }

    @Operation(summary = "API 상세 문서 조회")
    @GetMapping("/{projectId}/api-docs/{apiDocId}")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<GetApiDoc>> findProjectSingleApiDoc(
            @PathVariable String projectId,
            @PathVariable String apiDocId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                mProjectService.findProjectSingleApiDocs(new ObjectId(projectId), UUID.fromString(apiDocId))
        );
    }

    @Operation(summary = "프로젝트 API 문서 리스트 조회")
    @GetMapping("/{projectId}/api-docs")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<List<GetSimpleApiDoc>>> findProjectApiDocs(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                mProjectService.findProjectApiDocs(new ObjectId(projectId))
        );
    }

    @Operation(summary = "프로젝트 API 문서 삭제")
    @DeleteMapping("/{projectId}/api-docs/{apiDocId}")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<String>> deleteProjectApiDoc(
            @PathVariable String projectId,
            @PathVariable String apiDocId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.DELETE_SUCCESS,
                mProjectService.deleteProjectApiDoc(new ObjectId(projectId), UUID.fromString(apiDocId))
        );
    }

    @Operation(summary = "사용할 수 있는 PathVariable Name 조회")
    @GetMapping("/{projectId}/api-docs/{apiDocId}/names")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<List<String>>> findProjectValidPathVariableNames(
            @PathVariable String projectId,
            @PathVariable String apiDocId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                mProjectService.findProjectValidPathVariableNames(new ObjectId(projectId), UUID.fromString(apiDocId))
        );
    }

    @Operation(summary = "사용할 수 있는 도메인 Name 조회")
    @GetMapping("/{projectId}/api-docs/domain/names")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<List<String>>> findProjectValidDomainNames(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        mProjectService.liveProjectVuerd(new ObjectId(projectId));
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                mProjectService.findProjectValidDomainNames(new ObjectId(projectId))
        );
    }

    @Operation(summary = "Liveblocks 프로젝트 Readme 업데이트")
    @PostMapping("/{projectId}/readme/live")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<String>> liveUpdateProjectReadme(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                mProjectService.liveUpdateProjectReadme(new ObjectId(projectId))
        );
    }
}
