package io.ssafy.soupapi.domain.projectbuilder.dao;

import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;

public interface ProjectBuilderRepository {
    void packageBuilder(Project project);

    String ControllerBuilder(ApiDoc apiDoc);

    void copyDefaultProject(Project project);
}
