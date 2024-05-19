package io.ssafy.soupapi.global.config;

import io.ssafy.soupapi.global.security.filter.JwtAuthenticateFilter;
import io.ssafy.soupapi.global.security.filter.JwtAuthenticationEntryPoint;
import io.ssafy.soupapi.global.security.handler.CustomOAuth2FailHandler;
import io.ssafy.soupapi.global.security.handler.CustomOAuth2SuccessHandler;
import io.ssafy.soupapi.global.security.service.CustomOAuth2UserService;
import io.ssafy.soupapi.global.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private static final String[] URL_WHITE_LIST = {
            "/favicon.ico**/**",
            "/api/swagger-ui**/**", "/api/api-docs/**", "/api/swagger-resources/**",
            "/api/actuator**/**"
    };

    private final JwtAuthenticationEntryPoint entryPoint;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final CustomOAuth2FailHandler customOAuth2FailHandler;

    private final JwtService jwtService;

    // CORS 설정
    CorsConfigurationSource corsConfigurationSource() {
        final List<String> allowedHeaders = List.of("*");
        final List<String> allowedOriginPatterns = List.of(
                "http://localhost:8080",
                "http://localhost:3000",
                "https://so-up.store",
                "https://jiangxy.github.io", // websocket stomp 테스팅 : https://github.com/jiangxy/websocket-debug-tool
                "http://70.12.246.249:3000" // for ziu
        );

        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(allowedHeaders);
            config.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "DELETE", "PATCH", "CONNECT", "HEAD", "TRACE"));
            config.setAllowedOriginPatterns(allowedOriginPatterns); // 허용할 origin
            config.setAllowCredentials(true);
            return config;
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(HttpBasicConfigurer::disable) // JWT 인증 방식 사용하므로 httpBasic 인증은 막아두기, AbstractHttpConfigurer 해도 됨
                .formLogin(AbstractHttpConfigurer::disable) // JWT 인증 방식 사용하므로 httpBasic 인증은 막아두기
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 기반이 아님

                .csrf(AbstractHttpConfigurer::disable)

                .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))

                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll() // prefight CORS 이슈 때문에 넣었지만 안 넣어도 됨
                        .requestMatchers("/login", "/login/**", "/error", "/api/auth/**").permitAll()
                        .requestMatchers("/ws-stomp", "/ws-stomp/**", "/sub/**", "/pub/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(
                        oauth2 -> oauth2
                                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                                .successHandler(customOAuth2SuccessHandler)
                                .failureHandler(customOAuth2FailHandler)
                )
                .addFilterBefore(new JwtAuthenticateFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handler -> handler.authenticationEntryPoint(entryPoint))
        ;

        SecurityFilterChain chain = http.build();
//        log.info("SecurityFilterChain: {}", chain);
        return chain;
    }

    // Spring Security 필터를 통과하지 않는다. (이때 필터를 @Bean이 아닌 new filter() 로 등록해야 한다.)
    @Bean
    public WebSecurityCustomizer configureUrlWhiteList() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers(URL_WHITE_LIST);
    }

}