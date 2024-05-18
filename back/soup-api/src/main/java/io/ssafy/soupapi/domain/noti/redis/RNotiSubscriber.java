package io.ssafy.soupapi.domain.noti.redis;

import io.ssafy.soupapi.domain.noti.application.EmitterNotiService;
import io.ssafy.soupapi.domain.noti.dto.Event;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RNotiSubscriber implements MessageListener {

    private final RedisTemplate<String, Event> redisTemplateEvent;
    private final EmitterNotiService emitterNotiService;
    private RedisSerializer valueSerializer;

    @PostConstruct
    private void init() {
        valueSerializer = redisTemplateEvent.getValueSerializer();
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String topicName = new String(pattern);
        String receiverId = topicName.substring(5); // noti:{memberId}에서 memberId 부분
        Event event = (Event) valueSerializer.deserialize(message.getBody());
        log.info("subscriber가 역직렬화한 event는 {}", event);
        emitterNotiService.notify(receiverId, event);
    }

}
