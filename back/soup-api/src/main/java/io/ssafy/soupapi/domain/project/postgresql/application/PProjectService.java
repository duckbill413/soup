package io.ssafy.soupapi.domain.project.postgresql.application;

import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.global.security.TemporalMember;

public interface PProjectService {

    void registProject(String projectId, CreateProjectDto createProjectDto, TemporalMember temporalMember); // TODO: member security 적용
}
