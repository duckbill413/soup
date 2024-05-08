package io.ssafy.soupapi.domain.openvidu.application;

import io.openvidu.java.client.*;
import io.ssafy.soupapi.domain.openvidu.dto.response.UserConnection;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class OpenViduService {

    private final OpenVidu openVidu;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public OpenViduService(OpenVidu openVidu, RedisTemplate<String, String> redisTemplate) {
        this.openVidu = openVidu;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 프로젝트 ID를 기반으로 세션을 생성하거나 가져옵니다.
     * @param projectId 프로젝트 식별자
     * @return 세션 ID
     */
    public String getSessionId(String projectId) throws OpenViduJavaClientException, OpenViduHttpException {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String sessionId = ops.get(projectId);

        if (sessionId == null || openVidu.getActiveSession(sessionId) == null) {
            // 세션 생성
            Session session = openVidu.createSession(new SessionProperties.Builder().build());
            sessionId = session.getSessionId();
            ops.set(projectId, sessionId);
        }

        return sessionId;
    }

    /**
     * 세션에 대한 토큰을 생성합니다.
     * @param sessionId 세션 ID
     * @return UserConnection
     */
    public UserConnection getUserConnection(String sessionId) throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openVidu.getActiveSession(sessionId);
        if (session == null) throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_SESSION);
        ConnectionProperties properties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
                .role(OpenViduRole.PUBLISHER)
                .build();
        Connection connection=session.createConnection(properties);

        return UserConnection.builder()
                .sessionId(sessionId)
                .token(connection.getToken())
                .connectionId(connection.getConnectionId())
                .createdAt(connection.createdAt())
                .build();
    }


    /**
     * 사용자가 세션에서 나가는 것을 처리합니다.
     * @param sessionId 세션 ID
     * @param connectionId 사용자 토큰
     */
    public void leaveSession(String sessionId, String connectionId) throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openVidu.getActiveSession(sessionId);
        if (session == null) throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_SESSION);
        session.forceDisconnect(connectionId); // 사용자 연결을 강제로 끊습니다.
        checkAndCloseSessionIfEmpty(session);
    }

    /**
     * 세션의 모든 참여자가 나갔는지 확인하고, 아무도 없다면 세션을 종료합니다.
     * @param session 검사할 세션
     */
    private void checkAndCloseSessionIfEmpty(Session session) throws OpenViduJavaClientException, OpenViduHttpException {
        if (session != null && session.getActiveConnections().isEmpty()) {
            session.close(); // 세션에 남은 사용자가 없으면 세션을 종료합니다.
        }
    }

}
