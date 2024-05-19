package io.ssafy.soupapi.global.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class WebSocketPreHandler implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

//        log.info("message는 [{}]", message);
//        log.info("channel은 [{}]", channel);

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        if (accessor.getCommand().equals(StompCommand.CONNECT)) {
//
//        } else if (accessor.getCommand().equals(StompCommand.SUBSCRIBE)) {
//
//        }

        // TODO: JWT 토큰 확인

        return message;
    }

}
