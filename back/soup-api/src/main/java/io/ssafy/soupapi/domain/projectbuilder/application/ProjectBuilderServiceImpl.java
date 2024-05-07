package io.ssafy.soupapi.domain.projectbuilder.application;

import io.ssafy.soupapi.domain.project.mongodb.dao.MProjectRepository;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderInfo;
import io.ssafy.soupapi.domain.projectbuilder.dao.ProjectBuilderRepository;
import io.ssafy.soupapi.domain.projectbuilder.dto.request.ChangeProjectBuilderInfo;
import io.ssafy.soupapi.domain.projectbuilder.dto.response.GetProjectBuilderInfo;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ProjectBuilderServiceImpl implements ProjectBuilderService {
    private final MProjectRepository mProjectRepository;
    private final ProjectBuilderRepository projectBuilderRepository;

    @Override
    public void buildProject(String projectId) {
        var project = mProjectRepository.findById(new ObjectId(projectId)).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));
        // Copy Default Project
//        if (Objects.isNull(project.getInfo()) || Objects.isNull(project.getApiDocs())) {
//            throw new BaseExceptionHandler(ErrorCode.NEED_MORE_PROJECT_BUILD_DATA);
//        }
        // default project 복사
        projectBuilderRepository.copyDefaultProject(project);
        // Package Builder
//        projectBuilderRepository.packageBuilder(project);
        // Controller Builder


        // Service Builder
        // Repository Builder
        // Entity Builder
        // DTO Builder
    }

    @Transactional
    @Override
    public GetProjectBuilderInfo findBuilderInfo(String projectId) {
        var project = mProjectRepository.findProjectBuildInfo(new ObjectId(projectId)).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));

        if (Objects.isNull(project.getProjectBuilderInfo())) {
            var buildInfo = ProjectBuilderInfo.builder()
                    .type("Gradle - Groovy")
                    .language("Java")
                    .languageVersion("17")
                    .version("3.2.5")
                    .group("com.example")
                    .artifact("demo")
                    .name("demo")
                    .description("demo project with soup!")
                    .packageName("com.example.demo")
                    .build();
            mProjectRepository.changeProjectBuildInfo(new ObjectId(projectId), buildInfo);
            return GetProjectBuilderInfo.of(buildInfo);
        }

        return GetProjectBuilderInfo.of(project.getProjectBuilderInfo());
    }

    @Transactional
    @Override
    public GetProjectBuilderInfo changeBuilderInfo(String projectId, ChangeProjectBuilderInfo changeProjectBuilderInfo) {
        var buildInfo = ChangeProjectBuilderInfo.to(changeProjectBuilderInfo);
        mProjectRepository.changeProjectBuildInfo(new ObjectId(projectId), buildInfo);
        return GetProjectBuilderInfo.of(buildInfo);
    }
}
