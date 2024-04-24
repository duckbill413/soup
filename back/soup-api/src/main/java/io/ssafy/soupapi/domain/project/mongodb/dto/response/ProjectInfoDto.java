package io.ssafy.soupapi.domain.project.mongodb.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.global.util.StringParserUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Schema(description = "프로젝트 정보")
public record ProjectInfoDto(
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
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @Schema(description = "프로젝트 지라 정보")
        ProjectKeyDto projectKey,
        @Schema(description = "프로젝트 관리툴 목록")
        List<ProjectToolDto> tools,
        @Schema(description = "프로젝트 팀 멤버 목록")
        List<ProjectTeamMember> teamMembers
) {
    @Builder
    public ProjectInfoDto {
        id = StringParserUtil.parseNullToEmpty(id);
        name = StringParserUtil.parseNullToEmpty(name);
        description = StringParserUtil.parseNullToEmpty(description);
        profileImgUrl = StringParserUtil.parseNullToEmpty(profileImgUrl);
        if (Objects.isNull(tools) || tools.isEmpty()) {
            tools = List.of();
        }
        if (Objects.isNull(teamMembers) || teamMembers.isEmpty()) {
            teamMembers = List.of();
        }
    }

    /**
     * Project to ProjectInfoDto 변환 메소드
     * 키 정보는 없이 조회
     *
     * @param project mongodb project object
     * @return ProjectInfoDto
     */
    public static ProjectInfoDto toProjectInfoDto(Project project) {
        return ProjectInfoDto.builder()
                .id(project.getId().toHexString())
                .name(project.getInfo().getName())
                .description(project.getInfo().getDescription())
                .profileImgUrl(project.getInfo().getImgUrl())
                .startDate(project.getInfo().getStartDate())
                .endDate(project.getInfo().getEndDate())
                .tools(project.getTools().stream().map(ProjectToolDto::toProjectToolDto).toList())
                .teamMembers(project.getTeamMembers().stream().map(ProjectTeamMember::toProjectTeamMember).toList())
                .build();
    }

    /**
     * Project to ProjectInfoDto 변환 메소드
     * 키 정보와 함께 조회
     *
     * @param project mongodb project object
     * @return ProjectInfoDto
     */
    public static ProjectInfoDto toProjectInfoWithKeyDto(Project project) {
        return ProjectInfoDto.builder()
                .id(project.getId().toHexString())
                .name(project.getInfo().getName())
                .description(project.getInfo().getDescription())
                .profileImgUrl(project.getInfo().getImgUrl())
                .startDate(project.getInfo().getStartDate())
                .endDate(project.getInfo().getEndDate())
                .projectKey(ProjectKeyDto.toProjectInfoDto(project.getInfo()))
                .tools(project.getTools().stream().map(ProjectToolDto::toProjectToolDto).toList())
                .teamMembers(project.getTeamMembers().stream().map(ProjectTeamMember::toProjectTeamMember).toList())
                .build();
    }
}
