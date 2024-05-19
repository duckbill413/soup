package io.ssafy.soupapi.domain.member.api;

import io.ssafy.soupapi.domain.member.application.MemberService;
import io.ssafy.soupapi.domain.member.dto.MemberInfoDto;
import io.ssafy.soupapi.domain.member.dto.response.GetLiveblocksTokenRes;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.ssafy.soupapi.global.util.FindEntityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Tag(name = "유저", description = "유저 정보 관련")
public class MemberController {

    private final MemberService memberService;
    private final FindEntityUtil findEntityUtil;

    @Operation(summary = "유저 정보 조회", description = "유저의 닉네임, 이메일 등의 정보를 조회")
    @GetMapping("")
    public ResponseEntity<BaseResponse<MemberInfoDto>> getMemberInfo(
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                findEntityUtil.findMemberById(userSecurityDTO.getId()).toMemberInfoDto()
        );
    }

    @Operation(summary = "Liveblocks 유저 ID 토큰 조회", description = "Liveblocks 측에서 생성한 유저 ID 토큰 반환")
    @GetMapping("/liveblocks")
    public ResponseEntity<BaseResponse<GetLiveblocksTokenRes>> getLiveblocksUserIdToken(
        @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.CHECK_SUCCESS,
                memberService.getLiveblocksUserIdToken(userSecurityDTO.getId())
        );
    }

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
