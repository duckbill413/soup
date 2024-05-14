package io.ssafy.soupapi.domain.noti.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {

    // 모든 Emitter를 저장하는 ConcurrentHashMap(thread-safe)
    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>(); // eventId : SseEmitter

    public void save(String id, SseEmitter emitter) {
        emitterMap.put(id, emitter);
    }

    public void deleteById(String id) {
        emitterMap.remove(id);
    }

    public SseEmitter get(String id) {
        return emitterMap.get(id);
    }

    public Map<String, SseEmitter> findAllEventCacheStartWithId(String memberId) {
        Map<String, SseEmitter> result = new HashMap<>();
        for (String key : emitterMap.keySet()) {
            if (key.startsWith(memberId)) result.put(key, emitterMap.get(key));
        }
        return result;
    }

    public Map<String, SseEmitter> findAllStartWithById(String memberId) {
        Map<String, SseEmitter> result = new HashMap<>();
        for (String key : emitterMap.keySet()) {
            if (key.startsWith(memberId)) result.put(key, emitterMap.get(key));
        }
        return result;
    }

}
