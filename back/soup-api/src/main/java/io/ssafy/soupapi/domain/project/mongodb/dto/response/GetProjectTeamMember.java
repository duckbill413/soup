package io.ssafy.soupapi.domain.project.mongodb.dto.response;

import io.ssafy.soupapi.domain.project.postgresql.entity.ProjectRole;
import io.ssafy.soupapi.global.util.StringParserUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Schema(description = "프로젝트 팀 멤버")
public record GetProjectTeamMember(
        @Schema(description = "팀 멤버 아이디")
        UUID id,
        @Schema(description = "팀 멤버 이메일")
        String email,
        @Schema(description = "팀 멤버 권한 목록")
        List<ProjectRole> roles
) {
    public GetProjectTeamMember {
        email = StringParserUtil.parseNullToEmpty(email);
        if (Objects.isNull(roles) || roles.isEmpty()) {
            roles = List.of();
        }
    }
}
