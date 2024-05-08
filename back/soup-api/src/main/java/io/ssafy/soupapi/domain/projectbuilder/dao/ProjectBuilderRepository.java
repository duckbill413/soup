package io.ssafy.soupapi.domain.projectbuilder.dao;

import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDoc;

import java.io.IOException;

public interface ProjectBuilderRepository {
    void packageBuilder(Project project) throws IOException;

    String controllerBuilder(ApiDoc apiDoc);

    void createDefaultProject(Project project) throws IOException;

    void createGlobalGroup(Project project) throws IOException;

    void createDomainPackages(Project project);
}
