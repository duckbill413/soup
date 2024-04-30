package io.ssafy.soupapi.global.security.handler;

import io.ssafy.soupapi.global.security.service.JwtService;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Value("${social-login.redirect-uri}")
    private String FE_REDIRECT_URI;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException {

        UserSecurityDTO userSecurityDTO = (UserSecurityDTO) authentication.getPrincipal();

        String accessToken = jwtService.createAccessToken(userSecurityDTO);
        String refreshToken = jwtService.createRefreshToken(userSecurityDTO);

        String redirectUri = UriComponentsBuilder.fromUriString(FE_REDIRECT_URI)
                .queryParam("access-token", accessToken)
                .queryParam("refresh-token", refreshToken)
//                .queryParam("roles", userSecurityDTO.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .toUriString();

        response.sendRedirect(redirectUri);
    }
}
