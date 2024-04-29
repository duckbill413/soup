package io.ssafy.soupapi.global.security.dao;

import io.ssafy.soupapi.global.security.exception.RefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class TokenRedisDao {
    private final StringRedisTemplate redisTemplate;
    private static final String REFRESH_HASH = "refresh-token:";

    public void save(UUID id, String refreshToken, long expireTime) {
        redisTemplate.opsForValue().set(REFRESH_HASH + id, refreshToken, expireTime, TimeUnit.SECONDS);
    }

    public String get(UUID id) throws RefreshTokenException {
        var token = redisTemplate.opsForValue().get(REFRESH_HASH + id);
        if (Objects.isNull(token)) {
            throw new RefreshTokenException(RefreshTokenException.REFRESH_TOKEN_ERROR.NO_REFRESH);
        }
        return token;
    }
}
