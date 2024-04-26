package io.ssafy.soupapi.domain.project.mongodb.application;

import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectProposal;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectProposal;
import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.global.security.TemporalMember;
import org.bson.types.ObjectId;

public interface MProjectService {
    ObjectId createProject(CreateProjectDto createProjectDto, TemporalMember temporalMember); // TODO: member security 적용

    GetProjectInfo findProjectInfoAndTools(ObjectId projectId);

    GetProjectProposal findProjectProposal(ObjectId projectId);

    GetProjectProposal updateProjectProposal(ObjectId projectId, UpdateProjectProposal updateProjectProposal);

    GetProjectInfo updateProjectInfo(ObjectId projectId, UpdateProjectInfo updateProjectInfo);

    GetProjectJiraKey findProjectJiraKey(ObjectId objectId);

    GetProjectJiraKey updateProjectJiraKey(ObjectId projectId, UpdateProjectJiraKey updateProjectJiraKey);
}
