package io.ssafy.soupapi.global.security.api;

import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.dto.RefreshTokenDto;
import io.ssafy.soupapi.global.security.dto.TokenDto;
import io.ssafy.soupapi.global.security.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "JWT 토큰 관리")
public class AuthController {

    private final JwtService jwtService;

    @Operation(summary = "액세스 토큰 재발급 요청", description = "액세스 토큰 없거나 만료됐다면 리프레스 토큰을 통해 두 토큰 모두 재발급을 요청")
    @PostMapping("/token/refresh")
    public ResponseEntity<BaseResponse<TokenDto>> regenerateJwtToken (
        @RequestBody RefreshTokenDto refreshTokenDto
    ) {
        TokenDto tokenDto = jwtService.regenerateJwtTokens(refreshTokenDto.refreshToken());
        return BaseResponse.success(
            SuccessCode.SELECT_SUCCESS,
            tokenDto
        );
    }

}
