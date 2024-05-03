package io.ssafy.soupapi.domain.project.mongodb.application;

import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectProposal;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectProposal;
import io.ssafy.soupapi.domain.project.mongodb.entity.issue.ProjectIssue;
import io.ssafy.soupapi.domain.project.mongodb.entity.vuerd.VuerdDoc;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface MProjectService {
    ObjectId createProject(UserSecurityDTO userSecurityDTO);

    GetProjectInfo findProjectInfoAndTools(ObjectId projectId);

    GetProjectProposal findProjectProposal(ObjectId projectId);

    GetProjectProposal updateProjectProposal(ObjectId projectId, UpdateProjectProposal updateProjectProposal);

    GetProjectInfo updateProjectInfo(ObjectId projectId, UpdateProjectInfo updateProjectInfo);

    GetProjectJiraKey findProjectJiraKey(ObjectId objectId);

    GetProjectJiraKey updateProjectJiraKey(ObjectId projectId, UpdateProjectJiraKey updateProjectJiraKey);

    PageOffsetResponse<List<ProjectIssue>> findProjectIssues(ObjectId projectId, PageOffsetRequest pageOffsetRequest);

    PageOffsetResponse<List<ProjectIssue>> updateProjectIssues(ObjectId projectId, List<ProjectIssue> issues, PageOffsetRequest pageOffsetRequest, UserSecurityDTO userSecurityDTO);

    VuerdDoc findProjectVuerd(ObjectId projectId);

    VuerdDoc changeProjectVuerd(ObjectId projectId, VuerdDoc vuerdDoc);
}
