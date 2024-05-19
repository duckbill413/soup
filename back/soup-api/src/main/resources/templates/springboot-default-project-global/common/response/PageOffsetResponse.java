package :springboot-project_package.global.common.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Offset 기반 Pagination Response")
@JsonPropertyOrder({"content", "pagination"})
public record PageOffsetResponse<T>(
        @Schema(description = "페이징 데이타")
        T content,
        @Schema(description = "pagination")
        OffsetPagination pagination
) {
}
