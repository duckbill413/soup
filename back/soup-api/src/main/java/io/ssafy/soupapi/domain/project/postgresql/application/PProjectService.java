package io.ssafy.soupapi.domain.project.postgresql.application;

import io.ssafy.soupapi.domain.project.postgresql.dto.response.SimpleProjectDto;
import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;

import java.util.List;

public interface PProjectService {

    void registProject(String projectId, CreateProjectDto createProjectDto, UserSecurityDTO userSecurityDTO);
    PageOffsetResponse<List<SimpleProjectDto>> findSimpleProjects(PageOffsetRequest pageOffset, UserSecurityDTO userSecurityDTO);

}
