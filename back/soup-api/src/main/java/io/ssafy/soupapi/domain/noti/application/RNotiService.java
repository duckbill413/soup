package io.ssafy.soupapi.domain.noti.application;

import io.ssafy.soupapi.domain.noti.dto.Event;
import io.ssafy.soupapi.domain.noti.redis.RNotiPublisher;
import io.ssafy.soupapi.domain.noti.redis.RNotiSubscriber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class RNotiService {

    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RNotiPublisher rNotiPublisher;
    private final RNotiSubscriber rNotiSubscriber;
    private Map<String, ChannelTopic> topics;

    private final String NOTI_TOPIC_PREFIX = "noti:";

    @PostConstruct
    private void init() {
        topics = new ConcurrentHashMap<>();
    }

    public void sendNoti(String memberId, Event event) {
        ChannelTopic topic = getTopic(memberId);
        rNotiPublisher.publish(topic, event);
    }

    // 채널 구독
    public ChannelTopic getTopic(String memberId) {
        String topicName = NOTI_TOPIC_PREFIX + memberId;
        ChannelTopic topic = topics.get(topicName);
        if (topic == null) {
            topic = new ChannelTopic(topicName);
            redisMessageListenerContainer.addMessageListener(rNotiSubscriber, topic);
            topics.put(topicName, topic);
        }
        return topic;
    }

}
