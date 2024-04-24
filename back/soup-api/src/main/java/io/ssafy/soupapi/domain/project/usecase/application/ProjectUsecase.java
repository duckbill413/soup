package io.ssafy.soupapi.domain.project.usecase.application;

import io.ssafy.soupapi.domain.project.mongodb.dto.response.ProjectInfoDto;
import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.global.security.TemporalMember;
import org.bson.types.ObjectId;

public interface ProjectUsecase {
    String createProject(CreateProjectDto createProjectDto, TemporalMember temporalMember); // TODO: member security 적용

    ProjectInfoDto findProjectInfo(ObjectId projectId, TemporalMember member);
}
