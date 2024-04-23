package io.ssafy.soupapi.global.exception;

import io.ssafy.soupapi.global.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class BaseExceptionHandler extends RuntimeException {
    private final ErrorCode errorCode;

    public BaseExceptionHandler(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BaseExceptionHandler(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
