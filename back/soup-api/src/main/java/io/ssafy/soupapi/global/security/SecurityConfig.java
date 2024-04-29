package io.ssafy.soupapi.global.security;

import io.ssafy.soupapi.global.security.filter.JwtAuthenticateFilter;
import io.ssafy.soupapi.global.security.handler.CustomOAuth2FailHandler;
import io.ssafy.soupapi.global.security.handler.CustomOAuth2SuccessHandler;
import io.ssafy.soupapi.global.security.service.CustomOAuth2UserService;
import io.ssafy.soupapi.global.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] URL_WHITE_LIST = {
            "/error", "/favicon.ico", "/login",
            "/api/swagger-ui.html", "/api/swagger-ui/**", "/api/api-docs/**", "/api/swagger-resources/**",
            "/api/actuator/**", "/api/actuator"
    };

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final CustomOAuth2FailHandler customOAuth2FailHandler;

    private final JwtService jwtService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable) // JWT 인증 방식 사용하므로 httpBasic 인증은 막아두기
                .formLogin(AbstractHttpConfigurer::disable) // JWT 인증 방식 사용하므로 httpBasic 인증은 막아두기
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 기반이 아님

                .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))

                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(URL_WHITE_LIST).permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .anyRequest().authenticated()
                )

                .oauth2Login(
                        oauth2 -> oauth2
                                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                                .successHandler(customOAuth2SuccessHandler)
                                .failureHandler(customOAuth2FailHandler)
                )

                .addFilterBefore(jwtAuthenticateFilter(), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    public JwtAuthenticateFilter jwtAuthenticateFilter() {
        return new JwtAuthenticateFilter(jwtService, URL_WHITE_LIST);
    }

    // CORS 설정
    CorsConfigurationSource corsConfigurationSource() {
        final List<String> allowedHeaders = List.of("*");
        final List<String> allowedOriginPatterns = List.of(
                "http://localhost:8080",
                "http://localhost:3000"
        );

        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(allowedHeaders);
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(allowedOriginPatterns); // 허용할 origin
            config.setAllowCredentials(true);
            return config;
        };
    }

}