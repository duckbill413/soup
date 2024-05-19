package io.ssafy.soupapi.domain.chat.application;

import io.ssafy.soupapi.domain.chat.redis.RedisSubscriber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatroomService {

    // topic에 발행되는 메시지를 처리하는 리스너
    private final RedisMessageListenerContainer redisMessageListenerContainer;

    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;

    // 채팅방에 대화 메시지 발행을 위한 redis topic(채팅방) 정보. key chatroomId : value ChannelTopic
    // pub/sub 통신을 위한 리스너가 설정된 topic들의 모음
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        topics = new ConcurrentHashMap<>();
    }

    public ChannelTopic enterChatroom(String chatroomId) {
        ChannelTopic topic = topics.get(chatroomId);
        if (topic == null) {
            topic = new ChannelTopic(chatroomId);
            redisMessageListenerContainer.addMessageListener(redisSubscriber, topic); // pub/sub 통신을 위해 리스너를 설정. 대화가 가능해진다.
            topics.put(chatroomId, topic);
        }
        return topic;
    }

}
