package io.ssafy.soupapi.domain.noti.dao;

import io.ssafy.soupapi.domain.noti.entity.MNoti;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class RNotiRepository {

    private static final String NOTI_HASH = "noti:";
    private final RedisTemplate<String, MNoti> redisTemplateNoti;

    private RedisSerializer keySerializer; // RedisTemplate<String, MNoti>에 대한 key 직렬화
    private RedisSerializer valueSerializer; // RedisTemplate<String, MNoti>에 대한 value 직렬화

    @PostConstruct
    private void init() {
        keySerializer = redisTemplateNoti.getKeySerializer();
        valueSerializer = redisTemplateNoti.getValueSerializer();
    }

    public MNoti findByNotiId(String memberId, ObjectId notiId) {
        Set<MNoti> notiSet = redisTemplateNoti.opsForZSet().range(NOTI_HASH + memberId, 0, -1);
        for (MNoti mNoti : notiSet) {
            if (mNoti.getId().equals(notiId)) {
                return mNoti;
            }
        }
        return null;
    }

    public void updateIsRead(MNoti mNoti, boolean isRead) {
        redisTemplateNoti.executePipelined( (RedisCallback<Object>) connection -> {
            connection.zSetCommands().zRem(
                keySerializer.serialize(NOTI_HASH + mNoti.getReceiverId()),
                valueSerializer.serialize(mNoti)
            );
            mNoti.setIsRead(isRead);
            connection.zAdd(
                keySerializer.serialize(NOTI_HASH + mNoti.getReceiverId()),
                mNoti.getCreatedAt().toEpochMilli(),
                valueSerializer.serialize(mNoti)
            );
            return null;
        });
    }

    public List<MNoti> getNoti(String memberId) {
        Set<MNoti> mNotiSet
            = redisTemplateNoti.opsForZSet().reverseRange(NOTI_HASH + memberId, 0, -1);
        return new ArrayList<>(mNotiSet);
    }

    public void saveNotisToRedis(List<MNoti> mNotis) {
        redisTemplateNoti.executePipelined( (RedisCallback<Object>) connection -> {
            for (MNoti mNoti : mNotis) {
                connection.zAdd(
                        keySerializer.serialize(NOTI_HASH + mNoti.getReceiverId()),
                        mNoti.getCreatedAt().toEpochMilli(),
                        valueSerializer.serialize(mNoti)
                );
            }
            return null;
        });
    }

}
