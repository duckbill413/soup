package io.ssafy.soupapi.domain.project.postgresql.application;

import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectInfo;
import io.ssafy.soupapi.domain.project.postgresql.dto.response.SimpleProjectDto;
import io.ssafy.soupapi.domain.project.usecase.dto.request.UpdateProjectImage;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;

import java.util.List;

public interface PProjectService {

    void registProject(String projectId, UserSecurityDTO userSecurityDTO);

    PageOffsetResponse<List<SimpleProjectDto>> findSimpleProjects(PageOffsetRequest pageOffset, UserSecurityDTO userSecurityDTO);

    void updateProjectInfo(String projectId, UpdateProjectInfo updateProjectInfo);

    void changeProjectImage(String projectId, UpdateProjectImage updateProjectImage);
}
