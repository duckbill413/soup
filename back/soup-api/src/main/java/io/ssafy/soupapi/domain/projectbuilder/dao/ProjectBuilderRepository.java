package io.ssafy.soupapi.domain.projectbuilder.dao;

import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;

import java.io.IOException;

public interface ProjectBuilderRepository {
    void packageBuilder(Project project) throws IOException;

    String ControllerBuilder(ApiDoc apiDoc);

    void copyDefaultProject(Project project);
}
