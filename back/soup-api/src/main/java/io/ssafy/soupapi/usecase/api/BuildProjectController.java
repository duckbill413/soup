package io.ssafy.soupapi.usecase.api;

import io.ssafy.soupapi.domain.project.mongodb.application.MProjectService;
import io.ssafy.soupapi.domain.projectbuilder.application.ProjectBuilderService;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.ssafy.soupapi.usecase.application.UpdateProjectInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Log4j2
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "프로젝트 빌더", description = "프로젝트 빌드 API Controller")
public class BuildProjectController {
    private final UpdateProjectInfoService updateProjectInfoService;
    private final MProjectService mProjectService;
    private final ProjectBuilderService projectBuilderService;
    private final ConcurrentHashMap<String, Lock> locks = new ConcurrentHashMap<>();

    @Operation(summary = "프로젝트 빌드")
    @PostMapping("/{projectId}/builder")
    @PreAuthorize("@authService.hasProjectRoleMember(#projectId, #userSecurityDTO.getId())")
    public ResponseEntity<BaseResponse<String>> buildProject(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        Lock lock = locks.computeIfAbsent(projectId, k -> new ReentrantLock());

        if (lock.tryLock()) {
            try {
                // 프로젝트 정보 동기화
                updateProjectInfoService.liveUpdateProjectInfo(projectId);
                // 프로젝트 ERD 동기화
                mProjectService.liveProjectVuerd(new ObjectId(projectId));
                // 프로젝트 API 문서 동기화
                mProjectService.liveProjectApiDoc(new ObjectId(projectId));
                // 프로젝트 빌드 정보 동기화
                projectBuilderService.liveChangeBuilderInfo(projectId);
                // 프로젝트 ReadMe 동기화
                mProjectService.liveUpdateProjectReadme(new ObjectId(projectId));
                // 프로젝트 빌드 작업
                return BaseResponse.success(
                        SuccessCode.INSERT_SUCCESS,
                        projectBuilderService.buildProject(projectId)
                );
            } finally {
                lock.unlock();
            }
        } else {
            throw new BaseExceptionHandler(ErrorCode.PROJECT_IS_NOW_BUILDING);
        }
    }
}
