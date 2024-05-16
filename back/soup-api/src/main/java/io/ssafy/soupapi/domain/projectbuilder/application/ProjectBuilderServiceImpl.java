package io.ssafy.soupapi.domain.projectbuilder.application;

import io.ssafy.soupapi.domain.project.constant.StepName;
import io.ssafy.soupapi.domain.project.mongodb.dao.MProjectRepository;
import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderDependency;
import io.ssafy.soupapi.domain.project.mongodb.entity.builder.ProjectBuilderInfo;
import io.ssafy.soupapi.domain.projectbuilder.dao.ProjectBuilderRepository;
import io.ssafy.soupapi.domain.projectbuilder.dao.ProjectStructureRepository;
import io.ssafy.soupapi.domain.projectbuilder.dto.liveblock.LiveProjectBuilderInfo;
import io.ssafy.soupapi.domain.projectbuilder.dto.request.ChangeProjectBuilderInfo;
import io.ssafy.soupapi.domain.projectbuilder.dto.response.BuiltStructure;
import io.ssafy.soupapi.domain.projectbuilder.dto.response.GetProjectBuilderInfo;
import io.ssafy.soupapi.domain.springinfo.dao.SpringDependencyRepository;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.external.liveblocks.application.LiveblocksComponent;
import io.ssafy.soupapi.global.external.s3.application.S3FileService;
import io.ssafy.soupapi.global.util.FolderZipper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ProjectBuilderServiceImpl implements ProjectBuilderService {
    private final MProjectRepository mProjectRepository;
    private final SpringDependencyRepository dependencyRepository;
    private final ProjectBuilderRepository projectBuilderRepository;
    private final ProjectStructureRepository projectStructureRepository;
    private final S3FileService s3FileService;
    private final MongoTemplate mongoTemplate;
    private final LiveblocksComponent liveblocksComponent;

    @Override
    public String buildProject(String projectId) {
        var project = mProjectRepository.findById(new ObjectId(projectId)).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT));
        if (Objects.isNull(project.getInfo())) {
            throw new BaseExceptionHandler(ErrorCode.NEED_PROJECT_BUILD_INFO);
        }
        if (Objects.isNull(project.getVuerd())) {
            throw new BaseExceptionHandler(ErrorCode.NEED_PROJECT_BUILD_ERD);
        }
        if (Objects.isNull(project.getApiDocs())) {
            throw new BaseExceptionHandler(ErrorCode.NEED_PROJECT_BUILD_APIDOC);
        }
        if (Objects.isNull(project.getProjectBuilderInfo())) {
            throw new BaseExceptionHandler(ErrorCode.NEED_PROJECT_BUILD_BUILDINFO);
        }

        try {
            // default project 복사
            File destinationFolder = projectBuilderRepository.createDefaultProject(project);
            // Package Builder
            projectBuilderRepository.packageBuilder(project);
            // global 폴더 복사 및 variable 치환
            projectBuilderRepository.createGlobalGroup(project);
            // domain package 생성
            projectBuilderRepository.createDomainPackages(project);
            // .gitkeep 파일 삭제
            projectBuilderRepository.deleteGitKeepFile(project);
            // class files
            projectBuilderRepository.replaceClassesVariables(project);
            // create method in controller and service
            projectBuilderRepository.projectMethodBuilder(project); // apidoc
            // create entity relationship
            projectBuilderRepository.insertEntityRelationShip(project);
            // create dto files
            projectBuilderRepository.createDtoClassFiles(project); // apidoc
            // update local file to s3 cloud
            return uploadLocalProjectFile(project, destinationFolder);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(ErrorCode.FAILED_TO_BUILD_PROJECT);
        }
    }

    private String uploadLocalProjectFile(Project project, File file) throws IOException {
        String sourceFolderPath = file.getAbsolutePath();
        String zipFilePath = file.getAbsolutePath() + ".zip";

        FolderZipper.zipFolder(sourceFolderPath, zipFilePath);
        var s3Url = s3FileService.uploadFile(project.getId().toHexString(), zipFilePath);
        updateFilePath(project, sourceFolderPath, zipFilePath, s3Url);
        return s3Url;
    }

    private void updateFilePath(Project project, String filePath, String zipFilePath, String s3Url) {
        Query query = new Query().addCriteria(Criteria.where("_id").is(project.getId()));
        Update update = new Update()
                .set("project_builder_info.springboot_file_path", filePath)
                .set("project_builder_info.springboot_zip_file_path", zipFilePath)
                .set("project_builder_info.springboot_s3_url", s3Url)
                .set("project_builder_info.project_built_at", LocalDateTime.now(ZoneId.of("Asia/Seoul")));

        var result = mongoTemplate.updateFirst(query, update, Project.class);
        if (result.wasAcknowledged() && (result.getMatchedCount() > 0 || result.getModifiedCount() > 0)) {
            return;
        }
        throw new BaseExceptionHandler(ErrorCode.FAILED_TO_UPDATE_PROJECT);
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

    @Override
    public String getBuildUrl(ObjectId projectId) {
        Query query = new Query().addCriteria(Criteria.where("_id").is(projectId));
        query.fields().include("project_builder_info.springboot_s3_url");

        var project = mongoTemplate.findOne(query, Project.class);
        if (project == null || project.getProjectBuilderInfo() == null) {
            throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT);
        }
        if (project.getProjectBuilderInfo().getS3Url() == null) {
            throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_BUILT_PROJECT);
        }
        return project.getProjectBuilderInfo().getS3Url();
    }

    @Transactional(readOnly = true)
    @Override
    public BuiltStructure findProjectBuiltInfo(String projectId) {
        // 빌드 정보 불러 오기
        var buildInfo = findBuilderInfo(projectId);
        if (Objects.isNull(buildInfo.s3Url())) {
            throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_BUILT_PROJECT);
        }

        var structure = projectStructureRepository.findProjectStructure(projectId, buildInfo);
        return new BuiltStructure(buildInfo, structure);
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
                .s3Url(null)
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

    @Override
    public GetProjectBuilderInfo liveChangeBuilderInfo(String projectId) {
        var liveBuilderInfo = liveblocksComponent.getRoomStorageDocument(projectId, StepName.BUILD, LiveProjectBuilderInfo.class);
        var dependencies = dependencyRepository.findByIdIsInOrBasicIsTrue(liveBuilderInfo.dependencies());
        var buildInfo = LiveProjectBuilderInfo.toProjectBuilderInfo(liveBuilderInfo, dependencies);
        mProjectRepository.changeProjectBuildInfo(new ObjectId(projectId), buildInfo);
        return GetProjectBuilderInfo.of(buildInfo);
    }
}
