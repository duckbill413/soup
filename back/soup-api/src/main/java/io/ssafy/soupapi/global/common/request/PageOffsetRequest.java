package io.ssafy.soupapi.global.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Schema(description = "Offset 기반 페이지 요청")
public record PageOffsetRequest(
        @Schema(description = "페이지 번호 (최초 페이지 번호: 1)")
        int page,
        @Max(value = 100, message = "최대 100개까지 한번에 호출할 수 있습니다.")
        @Schema(description = "페이지 크기")
        int size
) {
    public PageOffsetRequest {
        // Default page number
        if (page <= 0) {
            page = 1;
        }
        // Default page size
        if (size <= 0) {
            size = 10;
        }
    }

    /**
     * PageRequest 로 변환
     *
     * @param pageOffsetRequest parsable offset request object
     * @return PageRequest
     */
    public static PageRequest of(PageOffsetRequest pageOffsetRequest) {
        return PageRequest.of(pageOffsetRequest.page() - 1, pageOffsetRequest.size());
    }

    /**
     * PageRequest 로 변환
     *
     * @param pageOffsetRequest parsable offset request object
     * @param sort              sortable column info
     * @return PageRequest
     */
    public static PageRequest of(PageOffsetRequest pageOffsetRequest, Sort sort) {
        return PageRequest.of(pageOffsetRequest.page() - 1, pageOffsetRequest.size(), sort);
    }

    public long calculateOffset() {
        return (long) (page - 1) * size;
    }
}
