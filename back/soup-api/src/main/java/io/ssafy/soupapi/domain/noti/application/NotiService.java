package io.ssafy.soupapi.domain.noti.application;

import io.ssafy.soupapi.domain.chat.dto.request.ChatMessageReq;
import io.ssafy.soupapi.domain.member.dao.MemberRepository;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.noti.constant.NotiType;
import io.ssafy.soupapi.domain.noti.dao.NotiRepository;
import io.ssafy.soupapi.domain.noti.dao.RNotiRepository;
import io.ssafy.soupapi.domain.noti.dto.Event;
import io.ssafy.soupapi.domain.noti.dto.response.GetNotiRes;
import io.ssafy.soupapi.domain.noti.dto.response.NewNotiRes;
import io.ssafy.soupapi.domain.noti.entity.MNoti;
import io.ssafy.soupapi.domain.project.postgresql.dao.PProjectRepository;
import io.ssafy.soupapi.domain.project.postgresql.entity.Project;
import io.ssafy.soupapi.global.util.FindEntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Instant;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotiService {

    private final FindEntityUtil findEntityUtil;
    private final NotiRepository notiRepository;
    private final RNotiRepository rNotiRepository;

    private final MemberRepository memberRepository;
    private final PProjectRepository pProjectRepository;

    private final EmitterNotiService emitterNotiService;
    private final RNotiService rNotiService;

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
            rNotiRepository.updateIsRead(mNoti, true);
        }

        notiRepository.updateIsReadById(notiId, true);

        Event event = Event.builder().notiType(NotiType.READ_NOTI).build();
        rNotiService.sendNoti(memberId, event);
        return true;
    }

    /*------------------------------------ MongoDB ------------------------------------*/

    public MNoti generateMNoti(
            String chatroomId, String chatMessageId, ChatMessageReq chatMessageReq,
            String mentioneeId, Instant createdAt, NotiType notiType
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
                .notiType(notiType)
                .build();
    }

    /*---------------------------------------------------- SSE ----------------------------------------------------*/

    // 클라이언트가 구독을 위해 호출
    public SseEmitter subscribe(String memberId, String lastEventId) {
        long curTime = System.currentTimeMillis();
        String emitterId = memberId + "_" + curTime;
        String eventID = String.valueOf(curTime);

        SseEmitter emitter = emitterNotiService.createEmitter(emitterId);

        Event event = Event.builder().notiType(NotiType.SSE).lastEventId(lastEventId).build();
        emitterNotiService.sendToClient(emitter, emitterId, eventID, memberId, event);

        /*--------------------------- 통신 이상 등으로 수신하지 못한 알림이 있다면 ---------------------------*/

        if (lastEventId != null && !lastEventId.isEmpty()) {
            long lastEventIdToMiliSec = Long.parseLong(lastEventId);
            Instant createdAt = Instant.ofEpochMilli(lastEventIdToMiliSec);
            List<MNoti> unreceivedNotis =
                    notiRepository.findByReceiverIdAndCreatedAtAfterOrderByCreatedAtAsc(memberId, createdAt);

            // NewNotiRes를 만들기 위해 필요한 notifier, project 정보 획득
            Map<UUID, Member> notifierMap = new HashMap<>();
            Map<String, Project> projectMap = new HashMap<>();
            for (MNoti mNoti : unreceivedNotis) {
                notifierMap.put(UUID.fromString(mNoti.getSenderId()), null);
                projectMap.put(mNoti.getProjectId(), null);
            }
            List<Member> notifierList = memberRepository.findAllById(notifierMap.keySet().stream().toList());
            List<Project> projectList = pProjectRepository.findAllById(projectMap.keySet().stream().toList());
            for (Member notifier : notifierList) {
                notifierMap.put(notifier.getId(), notifier);
            }
            for (Project project : projectList) {
                projectMap.put(project.getId(), project);
            }

            for (MNoti mNoti : unreceivedNotis) {
                NewNotiRes newNotiRes = mNoti.generateNewNotiRes(
                        notifierMap.get(UUID.fromString(mNoti.getSenderId())).getProfileImageUrl(),
                        projectMap.get(mNoti.getProjectId()).getName()
                );
                event = Event.builder().notiType(mNoti.getNotiType()).data(newNotiRes).build();
                rNotiService.sendNoti(mNoti.getReceiverId(), event);
            }
        }

        return emitter;
    }

}
