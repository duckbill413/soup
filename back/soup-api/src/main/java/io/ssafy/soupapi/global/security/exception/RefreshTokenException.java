package io.ssafy.soupapi.global.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RefreshTokenException extends RuntimeException {
    private final REFRESH_TOKEN_ERROR errorCode;

    public RefreshTokenException(REFRESH_TOKEN_ERROR errorCode) {
        this.errorCode = errorCode;
    }

    @Getter
    public enum REFRESH_TOKEN_ERROR {
        NO_ACCESS(HttpStatus.UNAUTHORIZED, "No access"),
        BAD_ACCESS(HttpStatus.UNAUTHORIZED, "Bad access"),
        NO_REFRESH(HttpStatus.UNAUTHORIZED, "No refresh token"),
        OLD_REFRESH(HttpStatus.FORBIDDEN, "Old refresh token"),
        BAD_REFRESH(HttpStatus.FORBIDDEN, "Bad refresh token"),
        ;

        private final HttpStatus httpStatus;
        private final String message;
        REFRESH_TOKEN_ERROR(HttpStatus httpStatus, String message) {
            this.httpStatus = httpStatus;
            this.message = message;
        }
    }
}
