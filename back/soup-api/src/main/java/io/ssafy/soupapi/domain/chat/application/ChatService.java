package io.ssafy.soupapi.domain.chat.application;

import io.ssafy.soupapi.domain.chat.dto.request.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private static final String CHATROOM_HASH = "chatroom:";
    private final RedisTemplate<String, ChatMessageDto> redisTemplateChatMessage;

    // 대화 저장
    public void saveMessage(String chatroomId, ChatMessageDto chatMessageDto) {
        // TODO: 1. MongoDB 저장

        // 2. Redis 저장
        redisTemplateChatMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageDto.class));
        redisTemplateChatMessage.opsForList().rightPush(CHATROOM_HASH + chatroomId, chatMessageDto);

        // expire 을 이용해서, Key 를 만료시킬 수 있음
        // redisTemplateMessage.expire(messageDto.getRoomId(), 1, TimeUnit.MINUTES);
    }

}
