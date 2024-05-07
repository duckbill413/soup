package io.ssafy.soupapi.global.common.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Cursor 기반 페이지 요청")
public record PageCursorRequest<T>(
        @Schema(description = "요청 콘텐츠 Cursor")
        T cursor
) {
}
