package io.ssafy.soupapi.domain.chat.application;

import io.ssafy.soupapi.domain.chat.dto.ChatMessageRedis;
import io.ssafy.soupapi.domain.chat.dto.request.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private static final String CHATROOM_HASH = "chatroom:";
    private final RedisTemplate<String, ChatMessageRedis> redisTemplateChatMessage;

    // 대화 저장
    public void saveMessage(String chatroomId, ChatMessageDto chatMessageDto) {
        ChatMessageRedis chatMessageRedis = chatMessageDto.toChatMessageRedis();

        // TODO: 1. 채팅 메시지 -> MongoDB 저장
        saveMessageToMongoDb(chatroomId, chatMessageDto);

        // TODO: 2. 태그 알림 -> MongoDB 저장

        // 3. 채팅 메시지 -> Redis 저장
        saveMessageToRedis(chatroomId, chatMessageRedis);
    }

    @Async
    public void saveMessageToMongoDb(String chatroomId, ChatMessageDto chatMessageDto) {

    }

    public void saveMessageToRedis(String chatroomId, ChatMessageRedis chatMessageRedis) {
        redisTemplateChatMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageRedis.class));
        redisTemplateChatMessage.opsForList().rightPush(CHATROOM_HASH + chatroomId, chatMessageRedis);

        // expire 을 이용해서, Key 를 만료시킬 수 있음
        // redisTemplateMessage.expire(messageDto.getRoomId(), 1, TimeUnit.MINUTES);
    }

}
