package io.ssafy.soupapi.usecase.application;

import io.ssafy.soupapi.domain.project.constant.StepName;
import io.ssafy.soupapi.domain.project.mongodb.application.MProjectService;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectInfo;
import io.ssafy.soupapi.domain.project.postgresql.application.PProjectService;
import io.ssafy.soupapi.global.external.liveblocks.application.LiveblocksComponent;
import io.ssafy.soupapi.usecase.dto.liveblock.LiveUpdateProjectInfo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProjectInfoService {
    private final MProjectService mProjectService;
    private final PProjectService pProjectService;
    private final LiveblocksComponent liveblocksComponent;

    public GetProjectInfo updateProjectInfo(String projectId, UpdateProjectInfo updateProjectInfo) {
        var projectInfo = mProjectService.updateProjectInfo(new ObjectId(projectId), updateProjectInfo);
        pProjectService.updateProjectInfo(projectId, updateProjectInfo);
        return projectInfo;
    }

    public GetProjectInfo liveUpdateProjectInfo(String projectId) {
        var liveProjectInfo = liveblocksComponent.getRoomStorageDocument(projectId, StepName.OUTLINE, LiveUpdateProjectInfo.class);
        var updateProjectInfo = LiveUpdateProjectInfo.toUpdateProjectInfo(liveProjectInfo);
        var projectInfo = mProjectService.updateProjectInfo(new ObjectId(projectId), updateProjectInfo);
        pProjectService.updateProjectInfo(projectId, updateProjectInfo);
        return projectInfo;
    }
}
