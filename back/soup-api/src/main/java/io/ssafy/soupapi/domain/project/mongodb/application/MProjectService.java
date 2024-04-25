package io.ssafy.soupapi.domain.project.mongodb.application;

import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectProposal;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.ProjectInfoDto;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectProposal;
import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.domain.project.usecase.dto.request.InviteTeammate;
import io.ssafy.soupapi.global.security.TemporalMember;
import org.bson.types.ObjectId;
import org.springframework.transaction.annotation.Transactional;

public interface MProjectService {
    ObjectId createProject(CreateProjectDto createProjectDto, TemporalMember temporalMember); // TODO: member security 적용

    ProjectInfoDto findProjectInfo(ObjectId projectId);

    ProjectInfoDto findProjectInfoWithKey(ObjectId projectId);

    GetProjectProposal findProjectProposal(ObjectId projectId);

    GetProjectProposal updateProjectProposal(UpdateProjectProposal updateProjectProposal);

    @Transactional
    void addTeammate(InviteTeammate inviteTeammate, String username);
}
