package io.ssafy.soupapi.domain.project.usecase.application;

import io.ssafy.soupapi.domain.project.usecase.dto.request.UpdateProjectImage;
import io.ssafy.soupapi.domain.project.usecase.dto.CreateAiProposal;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;

public interface ProjectUsecase {

    String createProject(UserSecurityDTO userSecurityDTO);

    CreateAiProposal createAiProposal(CreateAiProposal createAiProposal);

    String changeProjectImage(String projectId, UpdateProjectImage updateProjectImage);
}
