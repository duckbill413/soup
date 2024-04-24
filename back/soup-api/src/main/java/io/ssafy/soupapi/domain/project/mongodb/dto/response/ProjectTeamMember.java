package io.ssafy.soupapi.domain.project.mongodb.dto.response;

import io.ssafy.soupapi.domain.project.mongodb.entity.ProjectRole;
import io.ssafy.soupapi.domain.project.mongodb.entity.TeamMember;
import io.ssafy.soupapi.global.util.StringParserUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Schema(description = "프로젝트 팀 멤버")
public record ProjectTeamMember(
        @Schema(description = "팀 멤버 아이디")
        UUID id,
        @Schema(description = "팀 멤버 이메일")
        String email,
        @Schema(description = "팀 멤버 권한 목록")
        List<ProjectRole> roles
) {
    public ProjectTeamMember {
        email = StringParserUtil.parseNullToEmpty(email);
        if (Objects.isNull(roles) || roles.isEmpty()) {
            roles = List.of();
        }
    }

    public static ProjectTeamMember toProjectTeamMember(TeamMember teamMember) {
        return ProjectTeamMember.builder()
                .id(teamMember.getId())
                .email(teamMember.getEmail())
                .roles(teamMember.getRoles())
                .build();
    }
}
