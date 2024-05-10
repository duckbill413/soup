package io.ssafy.soupapi.domain.project.mongodb.application;

import io.ssafy.soupapi.domain.project.mongodb.dto.request.*;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.*;
import io.ssafy.soupapi.domain.project.mongodb.entity.issue.ProjectIssue;
import io.ssafy.soupapi.domain.project.usecase.dto.request.UpdateProjectImage;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.UUID;

public interface MProjectService {
    ObjectId createProject(UserSecurityDTO userSecurityDTO);

    GetProjectInfo findProjectInfoAndTools(ObjectId projectId);

    GetProjectProposal findProjectProposal(ObjectId projectId);

    GetProjectProposal updateProjectProposal(ObjectId projectId, UpdateProjectProposal updateProjectProposal);

    GetProjectInfo updateProjectInfo(ObjectId projectId, UpdateProjectInfo updateProjectInfo);

    GetProjectJiraKey findProjectJiraKey(ObjectId objectId);

    GetProjectJiraKey updateProjectJiraKey(ObjectId projectId, UpdateProjectJiraKey updateProjectJiraKey);

    PageOffsetResponse<List<ProjectIssue>> findProjectIssues(ObjectId projectId, PageOffsetRequest pageOffsetRequest);

    PageOffsetResponse<List<ProjectIssue>> updateProjectIssues(ObjectId projectId, List<ProjectIssue> issues, PageOffsetRequest pageOffsetRequest);

    Object findProjectVuerd(ObjectId projectId);

    Object changeProjectVuerd(ObjectId projectId, Object vuerdDoc);

    List<GetSimpleApiDoc> findProjectApiDocs(ObjectId projectId);

    List<String> findProjectValidPathVariableNames(ObjectId projectIdl, UUID apiDocId);

    GetApiDoc findProjectSingleApiDocs(ObjectId projectId, UUID apiDocId);

    List<String> findProjectValidDomainNames(ObjectId projectId);

    String updateProjectApiDoc(String projectId, UpdateApiDoc updateApiDoc);

    void changeProjectImage(ObjectId projectId, UpdateProjectImage updateProjectImage);
}
