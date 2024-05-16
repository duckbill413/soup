package io.ssafy.soupapi.domain.openvidu.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserConnection {
    private String sessionId;
    private String token;
    private String connectionId;
    private String createdAt;
    private Long expireTime;
}
