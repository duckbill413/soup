package io.ssafy.soupapi.domain.project.postgresql.application;

import io.ssafy.soupapi.domain.project.mongodb.entity.ProjectRole;
import io.ssafy.soupapi.domain.project.postgresql.dto.response.SimpleProjectDto;
import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import io.ssafy.soupapi.global.security.TemporalMember;
import org.bson.types.ObjectId;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PProjectService {

    void registProject(String projectId, CreateProjectDto createProjectDto, TemporalMember temporalMember); // TODO: member security 적용

    PageOffsetResponse<List<SimpleProjectDto>> findSimpleProjects(PageOffsetRequest pageOffset, TemporalMember member); // TODO: member security 적용

    @Transactional(readOnly = true)
    List<ProjectRole> getProjectRoles(ObjectId projectId, TemporalMember member);
}
