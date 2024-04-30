package io.ssafy.soupapi.global.security.filter;

import io.ssafy.soupapi.global.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticateFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        log.info("Request URI: {}", request.getRequestURI());
        log.info("Request Method: {}", request.getMethod());
        log.info("Request Params: {}", request.getParameterMap());
        log.info("Access-token: {}", request.getHeader("Authorization"));

        Authentication authentication = jwtService.authenticateAccessToken(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
