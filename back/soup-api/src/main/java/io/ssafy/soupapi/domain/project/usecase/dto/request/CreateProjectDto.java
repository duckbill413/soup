package io.ssafy.soupapi.domain.project.usecase.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Schema(description = "프로젝트 생성 요청")
public record CreateProjectDto(
        @NotEmpty(message = "프로젝트 이름을 지정해 주세요.")
        @Schema(description = "프로젝트 이름")
        String name,
        @Schema(description = "프로젝트 이미지")
        String imgUrl,
        @Pattern(regexp = "^\\d{8}$", message = "날짜 형식이 잘못되었습니다. (yyyyMMdd)")
        @Schema(description = "프로젝트 시작일")
        String startDate,
        @Pattern(regexp = "^\\d{8}$", message = "날짜 형식이 잘못되었습니다. (yyyyMMdd)")
        @Schema(description = "프로젝트 종료일")
        String endDate
) {
    public LocalDate getStartDate() throws ParseException {
        return LocalDate.parse(startDate, DateTimeFormatter.BASIC_ISO_DATE);
    }

    public LocalDate getEndDate() throws ParseException {
        return LocalDate.parse(endDate, DateTimeFormatter.BASIC_ISO_DATE);
    }
}
