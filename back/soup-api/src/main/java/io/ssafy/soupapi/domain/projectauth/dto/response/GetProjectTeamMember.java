package io.ssafy.soupapi.domain.projectauth.dto.response;

import io.ssafy.soupapi.domain.project.postgresql.entity.ProjectRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Schema(description = "프로젝트 팀 멤버")
public record GetProjectTeamMember(
        @Schema(description = "팀 멤버 아이디")
        UUID id,
        @Schema(description = "팀 멤버 이메일")
        String email,
        @Schema(description = "팀원 닉네임")
        String nickname,
        @Schema(description = "팀원 프로필 사진")
        String profileImageUrl,
        @Schema(description = "팀원 Jira displayName")
        String displayName,
        @Schema(description = "팀원의 핸드폰 번호")
        String phone,
        @Schema(description = "팀 멤버 권한 목록")
        List<ProjectRole> roles
) {
    @Builder
    public GetProjectTeamMember {
        if (Objects.isNull(roles)) {
            roles = List.of();
        }
    }
}
