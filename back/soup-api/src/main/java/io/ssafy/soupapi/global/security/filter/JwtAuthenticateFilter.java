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


        log.info("Request URL: {}", request.getRequestURL());
//        log.info("Request URI: {}", request.getRequestURI());
        log.info("Request Method: {}", request.getMethod());
        log.info("Request Params: {}", request.getParameterMap());
        log.info("Access Token: {}", request.getHeader("Authorization"));

        log.info("Header Names: {}", request.getHeaderNames());

//        log.info("Servlet Path: {}", request.getServletPath());
//        log.info("Context Path: {}", request.getContextPath());

//        log.info("Http Servlet Mapping: {}", request.getHttpServletMapping());
//        log.info("Path Info: {}", request.getPathInfo());
//
//        log.info("Http origin: {}", request.getHeader(org.springframework.http.HttpHeaders.ORIGIN));

        try {
            Authentication authentication = jwtService.authenticateAccessToken(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            request.setAttribute("tokenException", e);
        } finally {
            filterChain.doFilter(request, response);
        }

    }
}
