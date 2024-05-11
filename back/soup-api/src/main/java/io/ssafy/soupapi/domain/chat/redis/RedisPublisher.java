package io.ssafy.soupapi.domain.chat.redis;

import io.ssafy.soupapi.domain.chat.dto.response.ChatMessageRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplateJackson;

    // redis topic에 메시지 발행. 메시지를 발행 후, 대기 중이던 RedisSubscriber가 메시지를 처리.
    public void publish(ChannelTopic topic, ChatMessageRes chatMessageRes) {
        redisTemplateJackson.convertAndSend(topic.getTopic(), chatMessageRes);
    }

}
