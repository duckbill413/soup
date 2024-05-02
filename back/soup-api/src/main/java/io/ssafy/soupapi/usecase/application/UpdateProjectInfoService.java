package io.ssafy.soupapi.usecase.application;

import io.ssafy.soupapi.domain.project.mongodb.application.MProjectService;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectInfo;
import io.ssafy.soupapi.domain.project.postgresql.application.PProjectService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProjectInfoService {
    private final MProjectService mProjectService;
    private final PProjectService pProjectService;

    public GetProjectInfo updateProjectInfo(String projectId, UpdateProjectInfo updateProjectInfo) {
        var projectInfo = mProjectService.updateProjectInfo(new ObjectId(projectId), updateProjectInfo);
        pProjectService.updateProjectInfo(projectId, updateProjectInfo);
        return projectInfo;
    }
}
