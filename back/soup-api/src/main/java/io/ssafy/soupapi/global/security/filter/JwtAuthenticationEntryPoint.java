package io.ssafy.soupapi.global.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * @ExceptionHandler는 controller 계층의 예외만 처리한다.
 * 하지만 Spring Security는 controller에 도달하기 전 filterChain에서 예외를 발생시킨다.
 * Spring Security는 사용자가 인증되지 않았거나 AuthenticationException 발생 시 AuthenticationEntryPoint에서 예외 처리를 시도한다.
 * 그러므로 AuthenticationEntryPoint를 적절히 구현해 Spring Security에서 예외를 처리할 수 있다.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationEntryPoint(
        @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver // HandlerExceptionResolver 빈이 2 종류가 잇어서 @Qualifier로 특정
    ) {
        this.resolver = resolver;
    }

    // @ControllerAdvice에서 모든 예외를 처리하여 응답할 것이므로 여기에 별다른 로직은 작성하지 않고 HandlerExceptionResolver에 예외 처리를 위임한다.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        resolver.resolveException(request, response, null, (Exception) request.getAttribute("tokenException"));
    }
}
