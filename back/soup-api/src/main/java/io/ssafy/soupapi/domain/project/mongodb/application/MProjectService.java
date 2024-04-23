package io.ssafy.soupapi.domain.project.mongodb.application;

import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.global.security.TemporalMember;

public interface MProjectService {
    String createProject(CreateProjectDto createProjectDto, TemporalMember temporalMember); // TODO: member security 적용
}
