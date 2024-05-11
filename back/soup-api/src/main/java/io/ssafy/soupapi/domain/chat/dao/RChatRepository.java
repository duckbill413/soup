package io.ssafy.soupapi.domain.chat.dao;

import io.ssafy.soupapi.domain.chat.dto.RChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class RChatRepository {

    private static final String CHATROOM_HASH = "chatroom:";
    private static final String CHATROOM_HASH_PATTERN = "chatroom:*";

    private final RedisTemplate<String, RChatMessage> redisTemplateChatMessage;

    public void saveMessageToRedis(String chatroomId, RChatMessage RChatMessage, long sentAt) {
        redisTemplateChatMessage.opsForZSet().add(CHATROOM_HASH + chatroomId, RChatMessage, sentAt);
    }

    public List<RChatMessage> getMessageByIndex(String chatroomId, int startIndex, int endIndex) {
        Set<RChatMessage> RChatMessageSet = redisTemplateChatMessage.opsForZSet().range(CHATROOM_HASH + chatroomId, startIndex, endIndex);
        return new ArrayList<>(RChatMessageSet);
    }

    public List<RChatMessage> getNMessagesBefore(String chatroomId, Long before, long offset, long size) {
        Set<RChatMessage> RChatMessageSet
                = redisTemplateChatMessage.opsForZSet().reverseRangeByScore(CHATROOM_HASH + chatroomId, 0, before, offset, size);
        return new ArrayList<>(RChatMessageSet);
    }

    public void deleteMessageFromRedis(long minScore, long maxScore) {
        Set<String> keys = redisTemplateChatMessage.keys(CHATROOM_HASH_PATTERN);
        for (String key: keys) {
            redisTemplateChatMessage.opsForZSet().removeRangeByScore(key, minScore, maxScore);
        }
    }

}
