package io.ssafy.soupapi.domain.projectbuilder.application;

import io.ssafy.soupapi.domain.projectbuilder.dto.request.ChangeProjectBuilderInfo;
import io.ssafy.soupapi.domain.projectbuilder.dto.response.GetProjectBuilderInfo;
import org.bson.types.ObjectId;

public interface ProjectBuilderService {
    String buildProject(String projectId);

    GetProjectBuilderInfo changeBuilderInfo(String projectId, ChangeProjectBuilderInfo changeProjectBuilderInfo);

    GetProjectBuilderInfo findBuilderInfo(String projectId);

    String getBuildUrl(ObjectId projectId);

    GetProjectBuilderInfo liveChangeBuilderInfo(String projectId);
}
