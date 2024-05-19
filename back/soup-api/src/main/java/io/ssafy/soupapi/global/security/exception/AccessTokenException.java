package io.ssafy.soupapi.global.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AccessTokenException extends RuntimeException {

    private final ACCESS_TOKEN_ERROR errorCode;

    public AccessTokenException(ACCESS_TOKEN_ERROR errorCode) {
        this.errorCode = errorCode;
    }

    @Getter
    public enum ACCESS_TOKEN_ERROR {

        EMPTY(HttpStatus.UNAUTHORIZED, "토큰이 빈 값입니다."),
        BAD_TYPE(HttpStatus.UNAUTHORIZED, "토큰 유형은 Bearer Token이어야 합니다."),
        MAL_FORMED(HttpStatus.UNAUTHORIZED, "토큰의 형식이 잘못되었습니다."),
        BAD_SIGN(HttpStatus.UNAUTHORIZED, "토큰의 signature가 잘못되었습니다."),
        EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 ACCESS TOKEN 입니다."),
        ;

        private final HttpStatus httpStatus;
        private final String message;

        ACCESS_TOKEN_ERROR(HttpStatus httpStatus, String message) {
            this.httpStatus = httpStatus;
            this.message = message;
        }
    }
}
