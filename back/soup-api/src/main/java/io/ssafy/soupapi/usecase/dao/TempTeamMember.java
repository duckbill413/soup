package io.ssafy.soupapi.usecase.dao;

import io.ssafy.soupapi.domain.project.postgresql.entity.ProjectRole;
import lombok.Builder;

import java.util.Set;

@Builder
public record TempTeamMember(String code, String projectId, Set<ProjectRole> roles) {
}
