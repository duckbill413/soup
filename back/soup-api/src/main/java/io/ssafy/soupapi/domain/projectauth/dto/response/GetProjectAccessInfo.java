package io.ssafy.soupapi.domain.projectauth.dto.response;

import io.ssafy.soupapi.domain.project.postgresql.entity.ProjectRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "프로젝트 권한 확인")
public record GetProjectAccessInfo(
        @Schema(description = "프로젝트 권한")
        Set<ProjectRole> roles
) {
}
