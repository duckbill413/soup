package io.ssafy.soupapi.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class TemporalAuthenticateFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(getTemporalMember());
        filterChain.doFilter(request, response);
    }

    private Authentication getTemporalMember() {
        var temporalMember = TemporalMember.temporal()
                .id(UUID.fromString("8a94715d-5515-467b-8e00-98c87194aeed"))
                .email("olafminion0116@naver.com")
                .password("soup")
                .authorities(getAdminRole())
                .create();
        return new UsernamePasswordAuthenticationToken(
                temporalMember,
                temporalMember.getPassword(),
                temporalMember.getAuthorities()
        );
    }

    private static Collection<GrantedAuthority> getAdminRole() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authorities;
    }
}
