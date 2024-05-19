package io.ssafy.soupapi.domain.openvidu.application;

import io.openvidu.java.client.*;
import io.ssafy.soupapi.domain.openvidu.dto.response.UserConnection;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.config.OpenViduConfig;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Service
public class OpenViduService {

    private final OpenVidu openVidu;
    private final RedisTemplate<String, String> redisTemplate;
    private final RestTemplate restTemplate;
    private final String openviduUrl;
    private final String openviduSecret;

    private static final String OPENVIDU_REDIS_HASH = "openvidu_session:";
    private static final long ADDITIONAL_EXPIRE_SECONDS = 2 * 3600; // 2시간
    private static final Duration SESSION_TTL = Duration.ofHours(3);

    public OpenViduService(OpenVidu openVidu, RedisTemplate<String, String> redisTemplate, RestTemplate restTemplate, OpenViduConfig openViduConfig) {
        this.openVidu = openVidu;
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
        this.openviduUrl = openViduConfig.getOpenviduUrl();
        this.openviduSecret = openViduConfig.getOpenviduSecret();
    }

    /**
     * 프로젝트 ID를 기반으로 세션을 생성하거나 가져옵니다.
     * @param projectId 프로젝트 식별자
     * @return 세션 ID
     */
    public String getSessionId(String projectId) throws OpenViduJavaClientException, OpenViduHttpException {
        String sessionId = getSessionIdFromRedis(projectId);
        if (!isOpenviduServerActive()) throw new BaseExceptionHandler(ErrorCode.OPENVIDU_SERVER_ERROR);
        if (sessionId == null || !isOpenviduSessionActive(sessionId) || !isSessionActive(sessionId)) {
            deleteExistingSessionIfPresent(sessionId, projectId);
            return createSession(projectId);
        }
        return sessionId;
    }

    // 세션이 활성 상태인지 확인하는 헬퍼 메서드
    private boolean isSessionActive(String sessionId) {
        return openVidu.getActiveSession(sessionId) != null;
    }

    // 공통된 HTTP 요청을 처리하는 헬퍼 메서드
    private boolean isEndpointActive(String endpoint) {
        try {
            HttpHeaders headers = createBasicAuthHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    openviduUrl + endpoint,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            return false;
        }
    }

    // OpenVidu 서버에서 세션이 활성 상태인지 확인하는 메서드
    public boolean isOpenviduSessionActive(String sessionId) {
        String endpoint = "/openvidu/api/sessions/" + sessionId;
        return isEndpointActive(endpoint);
    }

    // OpenVidu 서버가 활성 상태인지 확인하는 메서드
    public boolean isOpenviduServerActive() {
        String endpoint = "/openvidu/api/config";
        return isEndpointActive(endpoint);
    }

    // 기본 인증 헤더를 생성하는 헬퍼 메서드
    private HttpHeaders createBasicAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("OPENVIDUAPP", openviduSecret);
        return headers;
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

    // 새 세션을 생성하고 Redis에 저장합니다.
    private String createSession(String projectId) throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openVidu.createSession(new SessionProperties.Builder().build());
        String sessionId = session.getSessionId();
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
        if (!isOpenviduServerActive()) throw new BaseExceptionHandler(ErrorCode.OPENVIDU_SERVER_ERROR);
        if (!isOpenviduSessionActive(sessionId)) throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_SESSION);
        Session session = getSession(sessionId);
        long expireTime = extendSessionExpiration(projectId);
        return createUserConnection(session, sessionId, expireTime);
    }

    // 세션을 가져오는 헬퍼 메서드
    private Session getSession(String sessionId) {
        Session session = openVidu.getActiveSession(sessionId);
        if (session == null) {
            throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_SESSION);
        }
        return session;
    }

    // 세션의 만료 시간을 연장하는 헬퍼 메서드
    private long extendSessionExpiration(String projectId) {
        Long expireTime = getSessionExpireTime(projectId);
        if (expireTime == null) {
            throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_EXPIRE_TIME);
        }
        if (expireTime > 0 && expireTime < 8 * 3600) {
            long newExpiration = expireTime + ADDITIONAL_EXPIRE_SECONDS;
            redisTemplate.expire(OPENVIDU_REDIS_HASH + projectId, Duration.ofSeconds(newExpiration));
            return newExpiration;
        }
        return expireTime;
    }

    // 세션의 만료 시간을 가져오는 헬퍼 메서드
    private Long getSessionExpireTime(String projectId) {
        return redisTemplate.getExpire(OPENVIDU_REDIS_HASH + projectId);
    }

    // 사용자 연결 정보를 생성하는 헬퍼 메서드
    private UserConnection createUserConnection(Session session, String sessionId, Long expireTime) throws OpenViduJavaClientException, OpenViduHttpException {
        ConnectionProperties properties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
                .role(OpenViduRole.PUBLISHER)
                .build();

        Connection connection = session.createConnection(properties);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
     * @param projectId 프로젝트 식별자
     */
    public void leaveSession(String sessionId, String connectionId, String projectId) throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openVidu.getActiveSession(sessionId);
        if (session == null) throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_SESSION);
        if (session.getConnection(connectionId)==null) throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_CONNECTION);
        session.forceDisconnect(connectionId);
        checkAndCloseSessionIfEmpty(session, projectId);
    }

    // 세션의 모든 참여자가 나갔는지 확인하고, 아무도 없다면 세션을 종료하는 헬퍼 메서드
    private void checkAndCloseSessionIfEmpty(Session session, String projectId) throws OpenViduJavaClientException, OpenViduHttpException {
        if (session != null && session.getActiveConnections().isEmpty()) {
            session.close();
            redisTemplate.delete(OPENVIDU_REDIS_HASH + projectId);
        }
    }
}
