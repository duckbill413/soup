package :springboot-project_package.global.common.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "cursor pagination")
@JsonPropertyOrder({"previousCursor", "currentCursor", "nextCursor", "hasBefore", "hasNext"})
public record CursorPagination<T>(
        @Schema(description = "이전 커서 정보")
        T previousCursor,
        @Schema(description = "현재 커서 정보")
        T currentCursor,
        @Schema(description = "다음 커서 정보")
        T nextCursor,
        @Schema(description = "이전 페이지 존재 여부")
        boolean hasBefore,
        @Schema(description = "다음 페이지 존재 여부")
        boolean hasNext
) {
}
