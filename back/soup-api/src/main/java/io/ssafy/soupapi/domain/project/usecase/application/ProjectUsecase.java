package io.ssafy.soupapi.domain.project.usecase.application;

import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectProposal;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectProposal;
import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.domain.project.usecase.dto.request.InviteTeammate;
import io.ssafy.soupapi.global.security.TemporalMember;
import jakarta.mail.MessagingException;

public interface ProjectUsecase {
    String createProject(CreateProjectDto createProjectDto, TemporalMember temporalMember); // TODO: member security 적용

    GetProjectInfo findProjectInfo(String projectId, TemporalMember member);

    GetProjectProposal findProjectProposal(String projectId, TemporalMember member);

    GetProjectProposal updateProjectProposal(String projectId, UpdateProjectProposal updateProjectProposal, TemporalMember member);

    String inviteProjectTeammate(InviteTeammate inviteTeammate, TemporalMember member) throws MessagingException;

    GetProjectInfo updateProjectInfo(String projectId, UpdateProjectInfo updateProjectInfo, TemporalMember member);

    GetProjectJiraKey findProjectJiraKey(String projectId, TemporalMember member);
}
