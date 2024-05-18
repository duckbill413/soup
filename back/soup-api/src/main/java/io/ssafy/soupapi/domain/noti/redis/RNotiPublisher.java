package io.ssafy.soupapi.domain.noti.redis;

import io.ssafy.soupapi.domain.noti.dto.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RNotiPublisher {

    private final RedisTemplate<String, Event> redisTemplateEvent;

    public void publish(ChannelTopic topic, Event event) {
        redisTemplateEvent.convertAndSend(topic.getTopic(), event);
    }

}
