package :springboot-project_package.global.common.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import :springboot-project_package.global.common.code.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.FieldError;

import java.util.List;

@Schema(description = "Error Response")
@JsonPropertyOrder({"status", "code", "message", "reason", "errors"})
public record ErrorResponse(
        @Schema(description = "HTTP 에러 상태") int status,
        @Schema(description = "Project 에러 코드") String code,
        @Schema(description = "Project 에러 메시지") String message,
        @Schema(description = "에러 발생 원인") String reason,
        @Schema(description = "에러 Field 리스트") List<FieldError> errors
) {
    public static ErrorResponse fail(ErrorCode code, String message) {
        return new ErrorResponse(
                code.getStatus(),
                code.getDivisionCode(),
                code.getMessage(),
                message == null ? "" : message,
                List.of()
        );
    }

    public static ErrorResponse fail(ErrorCode code, List<FieldError> errors, String message) {
        return new ErrorResponse(
                code.getStatus(),
                code.getDivisionCode(),
                code.getMessage(),
                message == null ? "" : message,
                errors == null ? List.of() : errors
        );
    }

}