package io.ssafy.soupapi.domain.member.api;

import io.ssafy.soupapi.domain.member.application.MemberService;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    public ResponseEntity<BaseResponse<String>> updateNickname(
            @NotEmpty(message = "닉네임을 확인해 주세요")
            @RequestParam("nickname") String nickname,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
            ) {
        return BaseResponse.success(
                SuccessCode.UPDATE_SUCCESS,
                memberService.updateNickname(nickname, userSecurityDTO)
        );
    }
}
