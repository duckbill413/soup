package io.ssafy.soupapi.domain.noti.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EmitterRepository {

    // ConcurrentHashMap : 멀티 스레드 환경에서 유용한 thread-safe -> 동시 사용자가 존재할 경우 유용
    // SseEmitter 객체를 저장하는 ConcurrentHashMap(thread-safe)
    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>(); // emitterId(memberId_system-milisec) : SseEmitter

    public void saveEmitter(String id, SseEmitter emitter) {
        emitterMap.put(id, emitter);
    }

    public void deleteEmitterById(String id) {
        emitterMap.remove(id);
    }

    public Map<String, SseEmitter> findAllEmitterStartWithMemberId(String memberId) {
        Map<String, SseEmitter> result = new ConcurrentHashMap<>();
        for (String key : emitterMap.keySet()) {
            if (key.startsWith(memberId)) result.put(key, emitterMap.get(key));
        }
        return result;
    }

}
