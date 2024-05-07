package :springboot-project_package.global.common.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder
@Schema(description = "offset pagination")
@JsonPropertyOrder({"page", "size", "total", "totalCount"})
public record OffsetPagination(
        @Schema(description = "현재 페이지 (1부터 시작")
        int page,
        @Schema(description = "페이지 크기")
        int size,
        @Schema(description = "전체 페이지 개수")
        int total,
        @Schema(description = "전체 콘텐츠 개수")
        int totalCount
) {
    public static OffsetPagination offset(int total, Long totalCount, Pageable pageable) {
        return OffsetPagination.builder()
                .page(pageable.getPageNumber() + 1)
                .size(pageable.getPageSize())
                .total(total)
                .totalCount(totalCount.intValue())
                .build();
    }
}
