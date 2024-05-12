package io.ssafy.soupapi.usecase.api;

import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectInfo;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.ssafy.soupapi.usecase.application.UpdateProjectInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@Tag(name = "프로젝트", description = "Project Info Update Controller")
public class UpdateProjectInfoController {
    private final UpdateProjectInfoService updateProjectInfoService;

    /**
     * 프로젝트 정보(개요 페이지) 수정
     * - 키, 프로젝트 이미지, 팀원 정보는 수정 안됨
     *
     * @param projectId         프로젝트 Id
     * @param updateProjectInfo 프로젝트 제안서 정보
     * @param userSecurityDTO   제안서를 수정하는 대상
     * @return 제안서 정보
     */

    @Operation(summary = "프로젝트 정보 수정", description = "프로젝트 개요 화면 정보 수정\n\n" +
        "Jira Key, 팀원 정보는 수정 불가\n\n" +
        "FE 신사 분께서 날짜 형식과 timezone은 KST(한국 시간) 아닌 **UTC**로 보내겠다고 하셨습니다. (예) 2024-05-07T15:00:00.000Z)\n\n" +
        "수정을 원치 않는 속성은 **null**로 보내주세요." +
        "<ul><li>프로젝트 이름</li><li>프로젝트 설명</li><li>프로젝트 이미지 링크</li><li>프로젝트 시작일</li><li>프로젝트 종료일</li><li>프로젝트 사용툴</li></ul>"
    )
    @PutMapping("/api/projects/{projectId}/info")
    @PreAuthorize("!@authService.hasViewerProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<GetProjectInfo>> updateProjectInfo(
            @PathVariable(name = "projectId") String projectId,
            @RequestBody UpdateProjectInfo updateProjectInfo,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                updateProjectInfoService.updateProjectInfo(projectId, updateProjectInfo)
        );
    }
}
