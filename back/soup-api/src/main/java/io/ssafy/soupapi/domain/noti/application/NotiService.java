package io.ssafy.soupapi.domain.noti.application;

import io.ssafy.soupapi.domain.noti.dao.EmitterRepository;
import io.ssafy.soupapi.domain.noti.dto.RMentionNoti;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotiService {

    private static final String MENTION_NOTI_HASH = "mention-noti:";
    private final RedisTemplate redisTemplateJackson;

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // 기본 타임 아웃
    private final EmitterRepository emitterRepository;

    public void saveMentionNotiToRedis(String chatroomId, RMentionNoti RMentionNoti, long sentAt) {
        redisTemplateJackson.setValueSerializer(new Jackson2JsonRedisSerializer<>(RMentionNoti.class));
        redisTemplateJackson.opsForZSet().add(MENTION_NOTI_HASH + chatroomId, RMentionNoti, sentAt);
    }

    public RMentionNoti generateMentionNotiRedis(String chatMessageId, String senderId, String mentioneeId) {
        return RMentionNoti.builder()
                .chatMessageId(chatMessageId)
                .mentionerId(senderId)
                .mentioneeId(mentioneeId)
                .build();
    }

    /*------------------------------------ SSE 푸시 알림 ------------------------------------*/

    // 클라이언트가 구독을 위해 호출
    public SseEmitter subscribe(String memberId) {
        SseEmitter emitter = createEmitter(memberId);
        sendToClient(memberId, "EventStream이 생성되었습니다 (memberId=" + memberId + ")");
        return emitter;
    }

    // memberId를 기준으로 이벤트 Emitter를 생성
    private SseEmitter createEmitter(String memberId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(memberId, emitter);

        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다
        emitter.onCompletion(() -> emitterRepository.deleteById(memberId));
        // Emitter가 타임아웃 되었을 때 (지정된 시간 동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다
        emitter.onTimeout(() -> emitterRepository.deleteById(memberId));

        return emitter;
    }

    public void notify(String memberId, Object event) {
        sendToClient(memberId,event);
    }

    /**
     * 클라이언트에 데이터를 전송
     * @param memberId 데이터를 받을 사용자의 memberId
     * @param data 전송할 데이터
     */
    private void sendToClient(String memberId, Object data) {
        SseEmitter emitter = emitterRepository.get(memberId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().id(String.valueOf(memberId)).name("sse").data(data));
            } catch (IOException e) {
                emitterRepository.deleteById(memberId);
                emitter.completeWithError(e);
            }
        }
    }

}
