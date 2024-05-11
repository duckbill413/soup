package io.ssafy.soupapi.domain.chat.dao;

import io.ssafy.soupapi.domain.chat.dto.RChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RChatRepository {

    private static final String CHATROOM_HASH = "chatroom:";
    private final RedisTemplate<String, RChatMessage> redisTemplateChatMessage;

    public void saveMessageToRedis(String chatroomId, RChatMessage RChatMessage, long sentAt) {
        redisTemplateChatMessage.opsForZSet().add(CHATROOM_HASH + chatroomId, RChatMessage, sentAt);

        // expire 을 이용해서, Key 를 만료시킬 수 있음
        // redisTemplateMessage.expire(messageDto.getRoomId(), 1, TimeUnit.MINUTES);
    }

    public List<RChatMessage> getMessage(String chatroomId, int size, int startIndex, int endIndex) {
        Set<RChatMessage> RChatMessageSet = redisTemplateChatMessage.opsForZSet().range(CHATROOM_HASH + chatroomId, startIndex, endIndex);
        return new ArrayList<>(RChatMessageSet);
    }

}
