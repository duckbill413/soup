package io.ssafy.soupapi.domain.noti.application;

import io.ssafy.soupapi.domain.noti.dto.RMentionNoti;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotiService {

    private static final String MENTION_NOTI_HASH = "mention-noti:";
    private final RedisTemplate redisTemplateJackson;

    public void saveMentionNotiToRedis(String chatroomId, RMentionNoti RMentionNoti, long sentAt) {
        redisTemplateJackson.setValueSerializer(new Jackson2JsonRedisSerializer<>(RMentionNoti.class));
//        redisTemplateJackson.opsForList().rightPush(MENTION_NOTI_HASH + chatroomId, mentionNotiRedis);
        redisTemplateJackson.opsForZSet().add(MENTION_NOTI_HASH + chatroomId, RMentionNoti, sentAt);
    }

    public RMentionNoti generateMentionNotiRedis(String chatMessageId, String senderId, String mentioneeId) {
        return RMentionNoti.builder()
                .chatMessageId(chatMessageId)
                .mentionerId(senderId)
                .mentioneeId(mentioneeId)
                .build();
    }

}
