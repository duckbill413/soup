package io.ssafy.soupapi.global.config;

import io.ssafy.soupapi.global.security.handler.WebSocketPreHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
//@EnableWebSocket // 웹소켓 서버 사용 (?)
@EnableWebSocketMessageBroker // STOMP 사용
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketPreHandler webSocketPreHandler;

    // 웹 소켓 연결을 위한 엔드포인트 설정 및 stomp sub/pub 엔드포인트 설정
    // withSockJS() (O) -> SockJS & STOMP 커넥션으로 http://localhost:8080/ws-stomp 로 요청
    // withSockJS() (X) -> STOMP만의 커넥션으로 ws://localhost:8080/ws-stomp로 요청
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp") // {context-path}/ws-stomp로 들어온 HTTP 연결을 WebSocket 연결로 업그레이드
                .setAllowedOriginPatterns(
                    "http://localhost:8080",
                    "https://jiangxy.github.io",
                    "http://localhost:3000",
                    "https://so-up.store"
                ); // 연결될 엔드포인트
                // .withSockJS(); // SocketJS 를 연결한다는 설정 -> 버전 낮은 브라우저에서도 적용 가능
        ;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지를 구독하는 요청 url => 즉 메시지 받을 때
        registry.enableSimpleBroker("/sub");

        // 메시지를 발행하는 요청 url => 즉 메시지 보낼 때
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketPreHandler);
    }

}
