package io.ssafy.soupapi.global.common.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;

@Schema(description = "Base Response")
@JsonPropertyOrder({"status", "message", "result"})
public record BaseResponse<T>(T result, int status, String message) {
    public static <T> ResponseEntity<BaseResponse<T>> success(SuccessCode successCode, T data) {
        return ResponseEntity
                .status(successCode.getStatus())
                .body(new BaseResponse<>(
                        data,
                        successCode.getStatus(),
                        successCode.getMessage()
                ));
    }
}