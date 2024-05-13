package io.ssafy.soupapi.domain.projectbuilder.application;

import io.ssafy.soupapi.domain.projectbuilder.dto.request.ChangeProjectBuilderInfo;
import io.ssafy.soupapi.domain.projectbuilder.dto.response.GetProjectBuilderInfo;

public interface ProjectBuilderService {
    String buildProject(String projectId);

    GetProjectBuilderInfo changeBuilderInfo(String projectId, ChangeProjectBuilderInfo changeProjectBuilderInfo);

    GetProjectBuilderInfo findBuilderInfo(String projectId);
}
