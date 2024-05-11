package io.ssafy.soupapi.global.scheduling;

import io.ssafy.soupapi.domain.chat.dao.RChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@RequiredArgsConstructor
@Service
public class SchedulerService {

    private final RChatRepository rChatRepository;

    /**
     * 실행 시간 1시간 전보다 전에 발행된 채팅 메시지들은 (redis에서) 삭제 (MongoDB엔 남아있음)
     */
    @Scheduled(fixedDelay = 600000) // 10분마다 실행
    public void deleteChatMessagesFromRedis() {
        long curMs = System.currentTimeMillis();
        LocalDateTime curLdt = Instant.ofEpochMilli(curMs).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        LocalDateTime hourAgoLdt = curLdt.minusHours(1).withSecond(0).withNano(0);
        long hourAgoScore = hourAgoLdt.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        log.info("현재 {}, redis 청소 작업을 시작합니다 (대상: {} 까지)", curLdt, hourAgoScore);

        rChatRepository.deleteMessageFromRedis(Long.MIN_VALUE, hourAgoScore);
    }

}
