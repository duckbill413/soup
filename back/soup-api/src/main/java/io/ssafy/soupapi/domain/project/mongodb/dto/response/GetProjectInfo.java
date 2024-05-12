package io.ssafy.soupapi.domain.project.mongodb.dto.response;

import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.global.util.DateConverterUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Schema(description = "프로젝트 정보")
public record GetProjectInfo(
        @Schema(description = "프로젝트 id")
        String id,
        @Schema(description = "프로젝트 이름")
        String name,
        @Schema(description = "프로젝트 설명")
        String description,
        @Schema(description = "프로젝트 프로필 이미지")
        String profileImgUrl,
        @Schema(description = "프로젝트 시작일")
        LocalDate startDate,
        @Schema(description = "프로젝트 종료일")
        LocalDate endDate,
        @Builder.Default
        @Schema(description = "프로젝트 관리툴 목록")
        List<GetProjectTool> tools
) {
    @Builder
    public GetProjectInfo {
        if (Objects.isNull(tools)) {
            tools = List.of();
        }
    }

    /**
     * Project to ProjectInfoDto 변환 메소드
     * 키 정보는 없이 조회
     *
     * @param project mongodb project object
     * @return ProjectInfoDto
     */
    public static GetProjectInfo toProjectInfoDto(Project project) {
        return GetProjectInfo.builder()
                .id(project.getId().toHexString())
                .name(project.getInfo().getName())
                .description(project.getInfo().getDescription())
                .profileImgUrl(project.getInfo().getImgUrl())
                .startDate(DateConverterUtil.instantToKstLd(project.getInfo().getStartDate()))
                .endDate(DateConverterUtil.instantToKstLd(project.getInfo().getEndDate()))
                .tools(project.getTools().stream().map(GetProjectTool::toProjectToolDto).toList())
                .build();
    }
}
