package io.ssafy.soupapi.domain.noti.api;

import io.ssafy.soupapi.domain.chat.dto.response.ChatMessageRes;
import io.ssafy.soupapi.domain.noti.application.NotiService;
import io.ssafy.soupapi.domain.noti.dto.response.GetNotiRes;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notis")
@Tag(name = "알림", description = "알림")
public class NotiController {

    private final NotiService notiService;

    // 유저가 /sub으로 구독하면, 백엔드에서 /sub/{memberId}로 구독된 것으로 처리
    @GetMapping(value = "/sub", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
        @AuthenticationPrincipal UserSecurityDTO userSecurityDTO,
        @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId // SSE 연결이 시간 만료 등의 이유로 끊어졌는데 알림이 발생하면? 이를 방지하기 위해, 클라이언트가 마지막으로 수신한 데이터의 ID값을 받는다. 이를 이용해 유실된 데이터를 다시 보내줄 수 있다.
    ) {
        String memberId = String.valueOf(userSecurityDTO.getId());
        return notiService.subscribe(memberId, lastEventId);
    }

//    @PostMapping("/send-data")
//    public void sendData(@AuthenticationPrincipal UserSecurityDTO userSecurityDTO) {
//        String memberId = String.valueOf(userSecurityDTO.getId());
//        notiService.notify(memberId, "hi - " + System.currentTimeMillis());
//    }

    @Operation(summary = "수신한 알림 조회", description = "유저가 수신한 모든 알림을 조회한다.\n\n" +
        "Query Parameter인 read의 값을 true 또는 false로 줌에 따라 필터링이 가능하다.\n\n" +
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
            @RequestParam(value = "notiId") String notiId,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                notiService.readNoti(userSecurityDTO.getId().toString(), notiId)
        );
    }

}
