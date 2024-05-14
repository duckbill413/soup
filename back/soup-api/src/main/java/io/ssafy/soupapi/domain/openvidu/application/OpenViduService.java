package io.ssafy.soupapi.domain.openvidu.application;

import io.openvidu.java.client.*;
import io.ssafy.soupapi.domain.openvidu.dto.response.UserConnection;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Service
public class OpenViduService {

    private final OpenVidu openVidu;
    private final RedisTemplate<String, String> redisTemplate;

    public OpenViduService(OpenVidu openVidu, RedisTemplate<String, String> redisTemplate) {
        this.openVidu = openVidu;
        this.redisTemplate = redisTemplate;
    }
    final String OPENVIDU_REDIS_HASH = "openvidu_session:";
    private static final long ADDITIONAL_EXPIRE_SECONDS = 2 * 3600; // 2시간
    private static final Duration SESSION_TTL = Duration.ofHours(3);

    /**
     * 프로젝트 ID를 기반으로 세션을 생성하거나 가져옵니다.
     * @param projectId 프로젝트 식별자
     * @return 세션 ID
     */
    public String getSessionId(String projectId) throws OpenViduJavaClientException, OpenViduHttpException {
        // Redis에서 세션 ID를 가져옴
        String sessionId = getSessionIdFromRedis(projectId);
        // 세션 ID가 null이거나 비활성 상태인 경우 새로운 세션을 생성
        if (sessionId == null || isSessionInactive(sessionId)) {
            // 기존 세션이 존재하면 Redis에서 삭제
            deleteExistingSessionIfPresent(sessionId, projectId);
            sessionId = createSession(projectId);
        }
        return sessionId;
    }

    // 세션이 비활성 상태인지 확인하는 헬퍼 메서드
    private boolean isSessionInactive(String sessionId) {
        return openVidu.getActiveSession(sessionId) == null;
    }

    // 기존 세션을 Redis에서 삭제하는 헬퍼 메서드
    private void deleteExistingSessionIfPresent(String sessionId, String projectId) {
        if (sessionId != null) {
            redisTemplate.delete(OPENVIDU_REDIS_HASH + projectId);
        }
    }


    // Redis에서 프로젝트 ID를 기반으로 세션 ID를 가져옵니다.
    private String getSessionIdFromRedis(String projectId) {
        return redisTemplate.opsForValue().get(OPENVIDU_REDIS_HASH + projectId);
    }

    /**
     * 새 세션을 생성하고 Redis에 저장합니다.
     * @param projectId 프로젝트 식별자
     * @return 생성된 세션 ID
     */
    private String createSession(String projectId) throws OpenViduJavaClientException, OpenViduHttpException {
        // OpenVidu로부터 새 세션을 생성
        Session session = openVidu.createSession(new SessionProperties.Builder().build());
        // 새 세션의 ID를 가져옴
        String sessionId = session.getSessionId();
        // Redis에 세션 정보를 저장하고 TTL을 설정
        redisTemplate.opsForValue().set(OPENVIDU_REDIS_HASH + projectId, sessionId, SESSION_TTL);
        return sessionId;
    }

    /**
     * 프로젝트 ID를 기반으로 세션 ID를 가져옵니다.
     * @param projectId 프로젝트 식별자
     * @return 세션 ID
     */
    public String getOnlySessionId(String projectId) throws BaseExceptionHandler {
        String sessionId = getSessionIdFromRedis(projectId);
        // 세션을 찾지 못한 경우 예외 처리
        if (sessionId == null) {
            throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_SESSION);
        }
        return sessionId;
    }

    /**
     * 세션에 대한 연결 정보를 가져오고 만료 시간을 연장하여 사용자 연결 정보를 반환합니다.
     * @param sessionId 세션 ID
     * @param projectId 프로젝트 식별자
     * @return 사용자 연결 정보
     */
    public UserConnection getUserConnection(String sessionId, String projectId) throws OpenViduJavaClientException, OpenViduHttpException {
        // 세션 가져오기
        Session session = getSession(sessionId);
        // 세션 만료 시간 연장
        long expireTime=extendSessionExpiration(projectId);
        // 사용자 연결 정보 생성
        return createUserConnection(session, sessionId, expireTime);
    }

    /**
     * 세션을 가져옵니다.
     * @param sessionId 세션 ID
     * @return 세션
     */
    private Session getSession(String sessionId) throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openVidu.getActiveSession(sessionId);
        // 세션이 없는 경우 예외 처리
        if (session == null) {
            throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_SESSION);
        }
        return session;
    }

    /**
     * 세션의 만료 시간을 연장합니다.
     * @param projectId 프로젝트 식별자
     */
    private long extendSessionExpiration(String projectId) {
        // 세션의 만료 시간을 가져옴
        Long expireTime = getSessionExpireTime(projectId);
        // 만료 시간이 없는 경우 예외 처리
        if (expireTime == null) {
            throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_EXPIRE_TIME);
        }
        // 만료 시간 연장
        if (expireTime > 0 && expireTime < 8 * 3600) {
            long newExpiration = expireTime + ADDITIONAL_EXPIRE_SECONDS;
            redisTemplate.expire(OPENVIDU_REDIS_HASH + projectId, Duration.ofSeconds(newExpiration));
            return newExpiration;
        }
        return expireTime;
    }


    // 세션의 만료 시간을 가져옵니다.
    private Long getSessionExpireTime(String projectId) {
        return redisTemplate.getExpire(OPENVIDU_REDIS_HASH + projectId);
    }

    /**
     * 사용자 연결 정보를 생성합니다.
     * @param session   세션
     * @param sessionId 세션 ID
     * @return 사용자 연결 정보
     */
    private UserConnection createUserConnection(Session session, String sessionId, Long expireTime) throws OpenViduJavaClientException, OpenViduHttpException {
        // 연결 속성 설정
        ConnectionProperties properties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
                .role(OpenViduRole.PUBLISHER)
                .build();
        // 세션에 연결 생성
        Connection connection = session.createConnection(properties);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 사용자 연결 정보 반환
        return UserConnection.builder()
                .sessionId(sessionId)
                .token(connection.getToken())
                .connectionId(connection.getConnectionId())
                .createdAt(dateFormat.format(new Date(connection.createdAt())))
                .expireTime(expireTime)
                .build();
    }

    /**
     * 사용자가 세션에서 나가는 것을 처리합니다.
     * @param sessionId 세션 ID
     * @param connectionId 사용자 토큰
     */
    public void leaveSession(String sessionId, String connectionId, String projectId) throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openVidu.getActiveSession(sessionId);
        if (session == null) throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_SESSION);
        session.forceDisconnect(connectionId); // 사용자 연결을 강제로 끊습니다.
        checkAndCloseSessionIfEmpty(session,projectId);
    }

    /**
     * 세션의 모든 참여자가 나갔는지 확인하고, 아무도 없다면 세션을 종료합니다.
     * @param session 검사할 세션
     */
    private void checkAndCloseSessionIfEmpty(Session session, String projectId) throws OpenViduJavaClientException, OpenViduHttpException {
        if (session != null && session.getActiveConnections().isEmpty()) {
            session.close(); // 세션에 남은 사용자가 없으면 세션을 종료합니다.
            redisTemplate.delete(OPENVIDU_REDIS_HASH + projectId);
        }
    }

}
