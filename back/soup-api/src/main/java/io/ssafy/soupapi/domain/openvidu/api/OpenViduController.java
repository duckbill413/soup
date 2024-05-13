package io.ssafy.soupapi.domain.openvidu.api;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.ssafy.soupapi.domain.openvidu.application.OpenViduService;
import io.ssafy.soupapi.domain.openvidu.dto.response.UserConnection;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
@RestController
@RequestMapping("/api/openvidu")
@RequiredArgsConstructor
@Tag(name="OpenVidu", description="OpenVidu API 연동 Controller")
public class OpenViduController {

    @Autowired
    private OpenViduService openViduService;

    /**
     * 프로젝트 ID를 기반으로 세션 ID를 생성하거나 가져옵니다.
     * @param projectId 프로젝트 ID
     * @return 세션 ID
     */

    @Operation(summary = "webRTC 세션 생성")
    @PostMapping("create/{projectId}")
    @PreAuthorize("@authService.hasChatProjectRoleMember(#projectId, #userSecurityDto.getId())")
    public ResponseEntity<BaseResponse<String>> createOrGetSession(
            @PathVariable("projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDto
    ) throws OpenViduJavaClientException, OpenViduHttpException {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                openViduService.getSessionId(projectId)
        );
    }

    /**
     * 지정된 세션에 대한 토큰을 생성합니다.
     * @param sessionId 세션 ID
     * @param projectId 프로젝트 ID
     * @return UserConnection
     */
    @Operation(summary = "webRTC 세션별 토큰 생성")
    @GetMapping("{projectId}/{sessionId}/token")
    @PreAuthorize("@authService.hasChatProjectRoleMember(#projectId, #userSecurityDto.getId())")
    public ResponseEntity<BaseResponse<UserConnection>> getToken(
            @PathVariable("sessionId") String sessionId,
            @PathVariable("projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDto
    ) throws OpenViduJavaClientException, OpenViduHttpException {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                openViduService.getUserConnection(sessionId)
                );
    }


    /**
     * 특정 세션에서 사용자가 나가는 요청을 처리합니다.
     * @param sessionId 세션 ID
     * @param projectId 프로젝트 ID
     * @param connectionId openvidu 사용자의 connectionID
     * @return 응답 상태
     */
    @Operation(summary = "webRTC 세션 퇴장 처리")
    @PostMapping("{projectId}/{sessionId}/{connectionId}/leave")
    @PreAuthorize("@authService.hasChatProjectRoleMember(#projectId, #userSecurityDto.getId())")
    public ResponseEntity<BaseResponse<String>> leaveSession(
            @PathVariable("sessionId") String sessionId,
            @PathVariable("projectId") String projectId,
            @PathVariable("connectionId") String connectionId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDto
    ) throws OpenViduJavaClientException, OpenViduHttpException {
        openViduService.leaveSession(sessionId, connectionId, projectId);
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                userSecurityDto.getId()+"님이 세션에서 퇴장하였습니다."
        );
    }

    /**
     * 프로젝트 아이디로 세션 ID를 조회합니다.
     * @param projectId 프로젝트 ID
     * @return UserConnection
     */
    @Operation(summary = "프로젝트 아이디로 세션 ID 조회")
    @GetMapping("search/{projectId}")
    @PreAuthorize("@authService.hasChatProjectRoleMember(#projectId, #userSecurityDto.getId())")
    public ResponseEntity<BaseResponse<String>> getSessionId(
            @PathVariable("projectId") String projectId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDto
    ) throws OpenViduJavaClientException, OpenViduHttpException {
        return BaseResponse.success(
                SuccessCode.CHECK_SUCCESS,
                openViduService.getOnlySessionId(projectId)
        );
    }


}

