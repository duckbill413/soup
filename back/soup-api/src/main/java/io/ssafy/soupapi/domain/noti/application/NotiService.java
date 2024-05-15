package io.ssafy.soupapi.domain.noti.application;

import io.ssafy.soupapi.domain.chat.dto.request.ChatMessageReq;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.noti.dao.EmitterRepository;
import io.ssafy.soupapi.domain.noti.dao.NotiRepository;
import io.ssafy.soupapi.domain.noti.dto.RMentionNoti;
import io.ssafy.soupapi.domain.noti.dto.response.GetNotiRes;
import io.ssafy.soupapi.domain.noti.dto.response.NewNotiRes;
import io.ssafy.soupapi.domain.noti.dto.response.SseNotiRes;
import io.ssafy.soupapi.domain.noti.entity.MNoti;
import io.ssafy.soupapi.global.util.FindEntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotiService {

    private final FindEntityUtil findEntityUtil;
    private static final String MENTION_NOTI_HASH = "mention-noti:";
    private final RedisTemplate redisTemplateJackson;

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // 기본 타임 아웃
    private final EmitterRepository emitterRepository;
    private final NotiRepository notiRepository;

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

    public MNoti generateMNoti(
            String chatroomId, String chatMessageId,
            ChatMessageReq chatMessageReq, String mentioneeId
    ) {
        Member mentionee = findEntityUtil.findMemberById(UUID.fromString(mentioneeId));
        String title = chatMessageReq.sender().getNickname() + "님이 " + mentionee.getNickname() + "님을 언급했습니다";
        return MNoti.builder()
                .title(title)
                .content(chatMessageReq.message())
                .senderId(chatMessageReq.sender().getMemberId())
                .receiverId(mentioneeId)
                .projectId(chatroomId)
                .chatMessageId(chatMessageId)
                .build();
    }

    public NewNotiRes generateNewNotiRes(MNoti mNoti, String notiPhotoUrl) {
        return mNoti.generateNewNotiRes(notiPhotoUrl);
    }

    /*------------------------------------ MongoDB 조회 ------------------------------------*/

    public GetNotiRes getNotis(String memberId, Boolean isRead) {
        List<MNoti> notiList;

        if (Objects.isNull(isRead)) {
            notiList = notiRepository.findByReceiverId(memberId);
        } else {
            notiList = notiRepository.findByReceiverIdAndIsRead(memberId, isRead);
        }

        return generateGetNotiResFromMNotiList(notiList);
    }

    public boolean readNoti(String notiId) {
        notiRepository.updateIsReadById(new ObjectId(notiId), true);
        return true;
    }

    private GetNotiRes generateGetNotiResFromMNotiList(List<MNoti> notiList) {
        Set<String> senderIdSet = new HashSet<>();
        for (MNoti mNoti : notiList) {
            senderIdSet.add(mNoti.getSenderId());
        }

        Map<String, String> senderMap = new HashMap<>();
        for (String senderId : senderIdSet) {
            String senderProfileImage = findEntityUtil.findMemberById(UUID.fromString(senderId)).getProfileImageUrl();
            senderMap.put(senderId, senderProfileImage);
        }

        GetNotiRes response = GetNotiRes.builder().build();
        for (MNoti mNoti : notiList) {
            response.getNotiList().add(
                mNoti.generateNewNotiRes(senderMap.get(mNoti.getSenderId()))
            );
        }

        return response;
    }

    /*------------------------------------ SSE 푸시 알림 ------------------------------------*/

    // 클라이언트가 구독을 위해 호출
    public SseEmitter subscribe(String memberId, String lastEventId) {
        String emitterId = memberId + "_" + System.currentTimeMillis();
        SseEmitter emitter = createEmitter(emitterId);

        // SSE 연결이 이뤄진 후 하나의 데이터도 전송되지 않고 SseEmitter의 유효 시간이 끝나면 503 응답이 발생한다. 그래서 연결 시 더미 데이터를 한 번 보내준다.
        sendToClient(emitter, emitterId, "sse", "EventStream이 생성되었습니다 (memberId=" + memberId + ")");

        // 클라이언트가 미수신한 event 목록이 존재할 경우 전송하여 event 유실을 예방
        if (!lastEventId.isEmpty()) {
            Map<String, SseEmitter> events = emitterRepository.findAllEventCacheStartWithId(memberId);
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), "mention", entry.getValue()));
        }

        return emitter;
    }

    // memberId를 기준으로 이벤트 Emitter를 생성
    private SseEmitter createEmitter(String emitterId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT); // 해당 시간만큼 SSE 연결이 유지되고 시간이 지나면 자동으로 클라이언트에서 재연결 요청을 보낸다.
        emitterRepository.save(emitterId, emitter);

        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        // Emitter가 타임아웃 되었을 때(지정된 시간 동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        return emitter;
    }

    // 안 읽은 알림이 몇 개인지 구독한 client에 전달
    public void notify(String receiverId, Object data, String eventName) {
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithById(receiverId);
        log.info("notify에서 sseEmitter는 {}개", sseEmitters.size());
        sseEmitters.forEach(
            (key, emitter) -> {
                // 데이터 캐시 저장 (유실된 데이터 처리하기 위함)
                // TODO: 이벤트 캐시 해야 되는 듯?

                if (data instanceof NewNotiRes newNotiRes) {
                    List<MNoti> unreadNotiList = notiRepository.findByReceiverIdAndIsRead(receiverId, false);
                    SseNotiRes response = SseNotiRes.builder()
                            .unreadNotiNum(unreadNotiList.size())
                            .newlyAddedNoti(newNotiRes)
                            .build();
                    sendToClient(emitter, key, eventName, response);
                }
            }
        );
    }

    private void sendToClient(SseEmitter emitter, String emitterId, String eventName, Object data) {
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .id(emitterId)
                        .name(eventName)
                        .data(data)
                );
            } catch (IOException e) {
                emitterRepository.deleteById(emitterId);
                emitter.completeWithError(e);
            }
        }
    }

}
