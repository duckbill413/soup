package io.ssafy.soupapi.domain.projectbuilder.application;

import io.ssafy.soupapi.domain.project.mongodb.dao.MProjectRepository;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderDependency;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderInfo;
import io.ssafy.soupapi.domain.projectbuilder.dao.ProjectBuilderRepository;
import io.ssafy.soupapi.domain.projectbuilder.dto.request.ChangeProjectBuilderInfo;
import io.ssafy.soupapi.domain.projectbuilder.dto.response.GetProjectBuilderInfo;
import io.ssafy.soupapi.domain.springinfo.dao.SpringDependencyRepository;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ProjectBuilderServiceImpl implements ProjectBuilderService {
    private final MProjectRepository mProjectRepository;
    private final SpringDependencyRepository dependencyRepository;
    private final ProjectBuilderRepository projectBuilderRepository;

    @Override
    public void buildProject(String projectId) {
        var project = mProjectRepository.findById(new ObjectId(projectId)).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));
        if (Objects.isNull(project.getInfo())) {
            throw new BaseExceptionHandler(ErrorCode.NEED_PROJECT_BUILD_INFO);
        }
        if (Objects.isNull(project.getVuerdDoc())) {
            throw new BaseExceptionHandler(ErrorCode.NEED_PROJECT_BUILD_ERD);
        }
//        if (Objects.isNull(project.getApiDocs())) {
//            throw new BaseExceptionHandler(ErrorCode.NEED_PROJECT_BUILD_APIDOC);
//        }
        if (Objects.isNull(project.getProjectBuilderInfo())) {
            throw new BaseExceptionHandler(ErrorCode.NEED_PROJECT_BUILD_BUILDINFO);
        }

        try {

            // default project 복사
            projectBuilderRepository.createDefaultProject(project);
            // Package Builder
            projectBuilderRepository.packageBuilder(project);
            // global 폴더 복사 및 variable 치환
            projectBuilderRepository.createGlobalGroup(project);
            // domain package 생성
            projectBuilderRepository.createDomainPackages(project);
            // Controller Builder


            // Service Builder
            // Repository Builder
            // Entity Builder
            // DTO Builder
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(ErrorCode.FAILED_TO_BUILD_PROJECT);
        }
    }

    /**
     * 프로젝트 빌드 정보 요청
     *
     * @param projectId 프로젝트 ID
     * @return 프로젝트 빌드 정보
     */
    @Transactional
    @Override
    public GetProjectBuilderInfo findBuilderInfo(String projectId) {
        var project = mProjectRepository.findProjectBuildInfo(new ObjectId(projectId)).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));

        if (Objects.isNull(project.getProjectBuilderInfo())) {
            return getDefaultProjectBuilderInfo(projectId);
        }

        return GetProjectBuilderInfo.of(project.getProjectBuilderInfo());
    }

    /**
     * 프로젝트 빌드 정보가 없을 경우 기본 빌드 정보를 생성
     * isBasic이 True인 경우 반드시 선택되는 의존성 정보
     *
     * @param projectId 프로젝트 ID
     * @return 프로젝트 최초 빌드 정보
     */
    private GetProjectBuilderInfo getDefaultProjectBuilderInfo(String projectId) {
        var dependencies = dependencyRepository.findByIdIsInOrBasicIsTrue(List.of());
        var buildInfo = ProjectBuilderInfo.builder()
                .type("Gradle-Groovy")
                .language("Java")
                .languageVersion("17")
                .version("3.2.5")
                .group("com.example")
                .artifact("demo")
                .name("demo")
                .dependencies(
                        dependencies.stream().map(d -> ProjectBuilderDependency.builder()
                                .id(d.getId())
                                .description(d.getDescription())
                                .category(d.getCategory())
                                .name(d.getName())
                                .code(d.getCode())
                                .basic(d.isBasic())
                                .build()).toList()
                )
                .description("demo project with soup!")
                .packageName("com.example.demo")
                .build();
        mProjectRepository.changeProjectBuildInfo(new ObjectId(projectId), buildInfo);
        return GetProjectBuilderInfo.of(buildInfo);
    }

    /**
     * 프로젝트 빌드 정보 업데이트
     * Dependency의 basic 칼럼값이 true인 경우 선택되지 않더라도 기본적으로 선택됩니다.
     *
     * @param projectId                프로젝트 ID
     * @param changeProjectBuilderInfo 수정된 프로젝트 빌드 정보
     * @return 수정 완료된 프로젝트 빌드 정보
     */
    @Transactional
    @Override
    public GetProjectBuilderInfo changeBuilderInfo(String projectId, ChangeProjectBuilderInfo changeProjectBuilderInfo) {
        var dependencies = dependencyRepository.findByIdIsInOrBasicIsTrue(changeProjectBuilderInfo.dependencies());
        var buildInfo = ChangeProjectBuilderInfo.to(changeProjectBuilderInfo, dependencies);
        mProjectRepository.changeProjectBuildInfo(new ObjectId(projectId), buildInfo);
        return GetProjectBuilderInfo.of(buildInfo);
    }
}
