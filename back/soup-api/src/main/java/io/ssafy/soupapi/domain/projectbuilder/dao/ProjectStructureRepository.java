package io.ssafy.soupapi.domain.projectbuilder.dao;

import io.ssafy.soupapi.domain.projectbuilder.dto.response.GetProjectBuilderInfo;

import java.util.Map;

public interface ProjectStructureRepository {
    Map<String, Object> findProjectStructure(String projectId, GetProjectBuilderInfo builderInfo);
}
