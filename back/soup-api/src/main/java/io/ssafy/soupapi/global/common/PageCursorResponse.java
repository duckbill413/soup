package io.ssafy.soupapi.global.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Cursor 기반 Pagination Response")
@JsonPropertyOrder({"content", "pagination"})
public record PageCursorResponse<T, U>(
        @Schema(description = "페이징 데이타")
        T content,
        @Schema(description = "pagination")
        CursorPagination<U> pagination
) {
}