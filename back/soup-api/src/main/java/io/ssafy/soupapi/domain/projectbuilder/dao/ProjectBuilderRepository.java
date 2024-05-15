package io.ssafy.soupapi.domain.projectbuilder.dao;

import io.ssafy.soupapi.domain.project.mongodb.entity.Project;

import java.io.File;
import java.io.IOException;

public interface ProjectBuilderRepository {
    void packageBuilder(Project project) throws IOException;

    File createDefaultProject(Project project) throws IOException;

    void createGlobalGroup(Project project) throws IOException;

    void createDomainPackages(Project project) throws IOException;

    void replaceClassesVariables(Project project) throws IOException;

    void projectMethodBuilder(Project project) throws IOException;

    void insertEntityRelationShip(Project project) throws IOException;
    void createDtoClassFiles(Project project) throws IOException;
    void deleteGitKeepFile(Project project);
}
