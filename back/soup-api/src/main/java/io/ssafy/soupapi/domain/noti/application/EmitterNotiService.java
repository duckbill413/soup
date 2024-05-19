package io.ssafy.soupapi.domain.noti.application;

import io.ssafy.soupapi.domain.noti.dao.EmitterRepository;
import io.ssafy.soupapi.domain.noti.dao.NotiRepository;
import io.ssafy.soupapi.domain.noti.dto.Event;
import io.ssafy.soupapi.domain.noti.dto.response.NewNotiRes;
import io.ssafy.soupapi.domain.noti.dto.response.SseNotiRes;
import io.ssafy.soupapi.domain.noti.entity.MNoti;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmitterNotiService {

    private static final Long DEFAULT_TIMEOUT = 1000L * 60 * 60; // 기본 타임 아웃 == 1시간
    private final NotiRepository notiRepository;
    private final EmitterRepository emitterRepository;

    // memberId를 기준으로 이벤트 Emitter를 생성
    public SseEmitter createEmitter(String emitterId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT); // 해당 시간만큼 SSE 연결이 유지되고 시간이 지나면 자동으로 클라이언트에서 재연결 요청을 보낸다.
        emitterRepository.saveEmitter(emitterId, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteEmitterById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteEmitterById(emitterId));
        emitter.onError((e) -> emitterRepository.deleteEmitterById(emitterId));

        return emitter;
    }

    // 안 읽은 알림이 몇 개인지 구독한 client에 전달
    public void notify(String receiverId, Event event) {
        // 여러 브라우저에 접속할 수 있으므로 emitter가 여러 개일 수 있다
        Map<String, SseEmitter> emitterMap = emitterRepository.findAllEmitterStartWithMemberId(receiverId);

        String eventId;
        if (event.getData() instanceof NewNotiRes newNotiRes) {
            eventId = String.valueOf(newNotiRes.getCreatedTime().toInstant().toEpochMilli());
        } else {
            eventId = String.valueOf(System.currentTimeMillis());
        }
        log.info("eventID는 : {}", eventId);

        emitterMap.forEach(
            (key, emitter) -> {
                sendToClient(emitter, key, eventId, receiverId, event);
            }
        );
    }

    public void sendToClient(SseEmitter emitter, String emitterId, String eventId, String receiverId, Event event) {
        try {
            List<MNoti> unreadNotiList = notiRepository.findByReceiverIdAndIsReadOrderByCreatedAtDesc(receiverId, false);
            SseNotiRes sseNotiRes = SseNotiRes.builder()
                    .unreadNotiNum(unreadNotiList.size())
                    .newlyAddedNoti(event.getData())
                    .build();

            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name(event.getNotiType().getEventName())
                    .data(sseNotiRes)
            );
        } catch (IOException e) {
            emitterRepository.deleteEmitterById(emitterId);
            emitter.completeWithError(e);
        }
    }

}
