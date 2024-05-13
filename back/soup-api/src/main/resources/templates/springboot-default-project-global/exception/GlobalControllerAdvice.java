package :springboot-project_package.global.exception;

import :springboot-project_package.global.common.code.ErrorCode;
import :springboot-project_package.global.common.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;

@RestControllerAdvice
public class GlobalControllerAdvice {
    // Custom ErrorCode를 기반으로 에러 처리
    @ExceptionHandler(BaseExceptionHandler.class)
    public ResponseEntity<ErrorResponse> handleCustomBaseExceptionHandler(BaseExceptionHandler e) {
        var response = ErrorResponse.fail(e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(response.status()).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleRuntimeExceptions(RuntimeException e) {
        e.printStackTrace();
        var response = ErrorResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());

        return ResponseEntity.status(response.status()).body(response);
    }

    /**
     * 예외 처리 되지 않은 모든 에러 처리
     *
     * @param e Exception
     * @return ResponseEntity
     */
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
        e.printStackTrace();
        var response = ErrorResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());

        return ResponseEntity.status(response.status()).body(response);
    }
}
