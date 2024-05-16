package io.ssafy.soupapi.domain.noti.application;

import io.ssafy.soupapi.domain.chat.dto.request.ChatMessageReq;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.noti.dao.EmitterRepository;
import io.ssafy.soupapi.domain.noti.dao.NotiRepository;
import io.ssafy.soupapi.domain.noti.dao.RNotiRepository;
import io.ssafy.soupapi.domain.noti.dto.response.GetNotiRes;
import io.ssafy.soupapi.domain.noti.dto.response.NewNotiRes;
import io.ssafy.soupapi.domain.noti.dto.response.SseNotiRes;
import io.ssafy.soupapi.domain.noti.entity.MNoti;
import io.ssafy.soupapi.global.util.FindEntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotiService {

    private final FindEntityUtil findEntityUtil;

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // 기본 타임 아웃
    private final EmitterRepository emitterRepository;
    private final NotiRepository notiRepository;
    private final RNotiRepository rNotiRepository;

    public GetNotiRes getNotis(String memberId, Boolean isRead) {
        List<MNoti> result;
        List<MNoti> rNotiList = new ArrayList<>();
        List<MNoti> mNotiList = new ArrayList<>();

        Map<String, Member> mentionerMap = new HashMap<>();
        Map<String, String> projectNameMap = new HashMap<>();

        /*------------------------------------ 1. Redis 탐색 ------------------------------------*/
        if (Objects.isNull(isRead)) {
            rNotiList = rNotiRepository.getNoti(memberId);
//            log.info("[수신 알림 조회] redis에서 {}개 발견", rNotiList.size());
        } else {
            // todo
        }
        for (MNoti mNoti : rNotiList) {
            mentionerMap.put(mNoti.getSenderId(), null);
            projectNameMap.put(mNoti.getProjectId(), null);
        }
        result = new ArrayList<>(rNotiList);

        /*------------------------------------ 2. MongoDB 탐색 ------------------------------------*/
        Instant mBeforeTime;
        if (rNotiList.isEmpty()) {
            mBeforeTime = Instant.now();
        } else {
            mBeforeTime = rNotiList.get(rNotiList.size() - 1).getCreatedAt();
        }
//        log.info("mBeforeTime은 {}", mBeforeTime);

        if (Objects.isNull(isRead)) {
            mNotiList = notiRepository.findByReceiverIdAndCreatedAtBeforeOrderByCreatedAtDesc(memberId, mBeforeTime);
        } else {
            mNotiList = notiRepository.findByReceiverIdAndIsReadOrderByCreatedAtDesc(memberId, isRead);
        }
        for (MNoti mNoti : mNotiList) {
            mentionerMap.put(mNoti.getSenderId(), null);
            projectNameMap.put(mNoti.getProjectId(), null);
        }
        result.addAll(mNotiList);
//        log.info("[수신 알림 조회] MongoDB에서 {}개 발견", mNotiList.size());

        /*----------------------------- 3. 알림 관련 추가적인 정보 조회 및 응답 가공 -----------------------------*/
        List<UUID> mentionerIdList = mentionerMap.keySet().stream()
                .map(UUID::fromString)
                .toList();
        mentionerMap = findEntityUtil.findAllMemberByIdAndGenerateMap(mentionerIdList);

        List<String> projectIdList = projectNameMap.keySet().stream().toList();
        projectNameMap = findEntityUtil.findAllProjectByIdAndGenerateMap(projectIdList);

        GetNotiRes response = GetNotiRes.builder().build();
        for (MNoti mNoti : result) {
            NewNotiRes newNotiRes = mNoti.generateNewNotiRes(
                mentionerMap.get(mNoti.getSenderId()).getProfileImageUrl(),
                projectNameMap.get(mNoti.getProjectId())
            );
            response.getNotiList().add(newNotiRes);
        }

        return response;
    }

    public boolean readNoti(String memberId, ObjectId notiId) {
        MNoti mNoti = rNotiRepository.findByNotiId(memberId, notiId);
        if (mNoti != null) {
            log.info("mNoti 나왔다!", mNoti);
            rNotiRepository.updateIsRead(mNoti, true);
        }

        notiRepository.updateIsReadById(notiId, true);

        List<MNoti> unreadNotis = notiRepository.findByReceiverIdAndIsReadOrderByCreatedAtDesc(memberId, false);
        notify(
            memberId,
            SseNotiRes.builder().unreadNotiNum(unreadNotis.size()).build(),
            "read-noti"
        );
        return true;
    }

    /*------------------------------------ Redis ------------------------------------*/

//    public NewNotiRes generateNewNotiRes(MNoti mNoti, String notiPhotoUrl) {
//        return mNoti.generateNewNotiRes(notiPhotoUrl);
//    }

    /*------------------------------------ MongoDB ------------------------------------*/

    public MNoti generateMNoti(
            String chatroomId, String chatMessageId,
            ChatMessageReq chatMessageReq, String mentioneeId, Instant createdAt
    ) {
        Member mentionee = findEntityUtil.findMemberById(UUID.fromString(mentioneeId));
        String title = chatMessageReq.sender().getNickname() + "님이 " + mentionee.getNickname() + "님을 언급했습니다.";
        return MNoti.builder()
                .title(title)
                .content(chatMessageReq.message())
                .senderId(chatMessageReq.sender().getMemberId())
                .receiverId(mentioneeId)
                .projectId(chatroomId)
                .chatMessageId(chatMessageId)
                .createdAt(createdAt)
                .build();
    }

    /*------------------------------------ SSE 푸시 알림 ------------------------------------*/

    // 클라이언트가 구독을 위해 호출
    public SseEmitter subscribe(String memberId, String lastEventId) {
        String emitterId = memberId + "_" + System.currentTimeMillis();
        SseEmitter emitter = createEmitter(emitterId);

        // SSE 연결이 이뤄진 후 하나의 데이터도 전송되지 않고 SseEmitter의 유효 시간이 끝나면 503 응답이 발생한다. 그래서 연결 시 더미 데이터를 한 번 보내준다.
        // 더미 데이터는 안 읽은 알림의 개수 전달
        List<MNoti> unreadNotis = notiRepository.findByReceiverIdAndIsReadOrderByCreatedAtDesc(memberId, false);
        SseNotiRes subRes = SseNotiRes.builder().unreadNotiNum(unreadNotis.size()).build();
        sendToClient(emitter, emitterId, "sse", subRes);

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
                    List<MNoti> unreadNotiList = notiRepository.findByReceiverIdAndIsReadOrderByCreatedAtDesc(receiverId, false);
                    SseNotiRes response = SseNotiRes.builder()
                            .unreadNotiNum(unreadNotiList.size())
                            .newlyAddedNoti(newNotiRes)
                            .build();
                    sendToClient(emitter, key, eventName, response);
                }

                else {
                    sendToClient(emitter, key, eventName, data);
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
