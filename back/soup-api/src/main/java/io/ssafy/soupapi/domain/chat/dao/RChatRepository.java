package io.ssafy.soupapi.domain.chat.dao;

import io.ssafy.soupapi.domain.chat.entity.RChatMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
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

    private RedisSerializer cmKeySerializer; // RedisTemplate<String, RChatMessage>에 대한 key 직렬화
    private RedisSerializer cmValueSerializer; // RedisTemplate<String, RChatMessage>에 대한 value 직렬화

    @PostConstruct
    private void init() {
        cmKeySerializer = redisTemplateChatMessage.getKeySerializer();
        cmValueSerializer = redisTemplateChatMessage.getValueSerializer();
    }

    public void insertMessage(String chatroomId, RChatMessage rChatMessage) {
        redisTemplateChatMessage.opsForZSet().add(
            CHATROOM_HASH + chatroomId, rChatMessage, rChatMessage.sentAt().toEpochMilli()
        );
    }

    public void insertMessages(String chatroomId, List<RChatMessage> rChatMessages) {
        redisTemplateChatMessage.executePipelined( (RedisCallback<Object>)connection -> {
            for (RChatMessage rChatMessage : rChatMessages) {
                connection.zAdd(
                    cmKeySerializer.serialize(CHATROOM_HASH + chatroomId),
                    rChatMessage.sentAt().toEpochMilli(),
                    cmValueSerializer.serialize(rChatMessage)
                );
            }
            return null;
        });

    }

    public List<RChatMessage> getNMessagesBefore(String chatroomId, Long before, long offset, long size) {
//        log.info("[RChatMessage] redis에서 score {} 이전의 메시지들을 조회합니다.", before);
        Set<RChatMessage> RChatMessageSet
                = redisTemplateChatMessage.opsForZSet().reverseRangeByScore(CHATROOM_HASH + chatroomId, 0, before, offset, size);
        return new ArrayList<>(RChatMessageSet);
    }

    // 채팅방마다 (chatroom:{chatroomId}) 저장된 채팅 메시지 개수를 200개로 유지한다.
    // 메시지 개수가 200개가 넘으면 오래된 것들은 삭제한다.
    public void deleteChatMessageOverSize() {
        redisTemplateChatMessage.execute((RedisCallback<Object>) connection -> {
            ScanOptions options = ScanOptions.scanOptions().match(CHATROOM_HASH_PATTERN).build();
            Cursor<String> result = redisTemplateChatMessage.scan(options);

            while (result.hasNext()) {
                String key = result.next(); // chatroom:{chatroomId}
//                log.info("redis에서 key {}를 조회합니다.", key);
                long size = redisTemplateChatMessage.opsForZSet().size(key);
                if (size > 200) {
                    long deleteNum = size - 200;
                    redisTemplateChatMessage.opsForZSet().removeRange(key, 0, deleteNum);
                }
            }

            return null;
        });
    }

}
