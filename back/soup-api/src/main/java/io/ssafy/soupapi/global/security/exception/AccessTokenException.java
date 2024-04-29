package io.ssafy.soupapi.global.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class AccessTokenException extends RuntimeException {

    private final ACCESS_TOKEN_ERROR error;

    public AccessTokenException(ACCESS_TOKEN_ERROR error) {
        this.error = error;
    }

    @Getter
    public enum ACCESS_TOKEN_ERROR {

        TOO_SHORT(HttpStatus.UNAUTHORIZED, "토큰이 null이거나 너무 짧습니다."),
        BAD_TYPE(HttpStatus.UNAUTHORIZED, "토큰 유형은 Bearer Token이어야 합니다."),
        MAL_FORMED(HttpStatus.FORBIDDEN, "토큰의 형식이 잘못되었습니다."),
        BAD_SIGN(HttpStatus.FORBIDDEN, "토큰의 signature가 잘못되었습니다."),
        EXPIRED(HttpStatus.FORBIDDEN, "토큰이 만료되었습니다."),
        ;

        private final HttpStatus httpStatus;
        private final String message;

        ACCESS_TOKEN_ERROR(HttpStatus httpStatus, String message) {
            this.httpStatus = httpStatus;
            this.message = message;
        }
    }
}
