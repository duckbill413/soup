package io.ssafy.soupapi.domain.noti.api;

import io.ssafy.soupapi.domain.noti.application.EmitterNotiService;
import io.ssafy.soupapi.domain.noti.application.NotiService;
import io.ssafy.soupapi.domain.noti.dto.response.GetNotiRes;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notis")
@Tag(name = "알림", description = "알림")
public class NotiController {

    private final NotiService notiService;

    @Operation(summary = "SSE 커넥션 생성", description = "SseEmitter를 반환한다." +
        "lastEventId를 통해 수신 못한 알림이 있다면 보내준다.\n\n")
    @GetMapping(value = "/sub", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
        @AuthenticationPrincipal UserSecurityDTO userSecurityDTO,
//        @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId // http 표준
        @RequestParam(value = "lastEventId", required = false, defaultValue = "") String lastEventId
    ) {
        String memberId = String.valueOf(userSecurityDTO.getId());
        return notiService.subscribe(memberId, lastEventId);
    }

    @Operation(summary = "수신한 알림 조회", description = "유저가 수신한 모든 알림을 조회한다.\n\n" +
        "Query Parameter인 read의 값을 true 또는 false로 줌에 따라 필터링이 가능하다. (<- 아직 API 미완성)\n\n" +
        "read 없이 요청 시, 읽은 거나 안 읽은 거나, 모든 알림을 조회한다.")
    @GetMapping(value="")
    public ResponseEntity<BaseResponse<GetNotiRes>> getNotis(
            @RequestParam(value = "read", required = false) Boolean read,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                notiService.getNotis(String.valueOf(userSecurityDTO.getId()), read)
        );
    }

    @Operation(summary = "알림 읽음 처리")
    @PostMapping(value="")
    public ResponseEntity<BaseResponse<Boolean>> readNoti(
            @RequestParam(value = "notiId") ObjectId notiId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                notiService.readNoti(userSecurityDTO.getId().toString(), notiId)
        );
    }

}
