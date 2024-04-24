package io.ssafy.soupapi.domain.project.usecase.application;

import io.ssafy.soupapi.domain.project.mongodb.application.MProjectService;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.ProjectInfoDto;
import io.ssafy.soupapi.domain.project.mongodb.entity.ProjectRole;
import io.ssafy.soupapi.domain.project.postgresql.application.PProjectService;
import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.global.security.TemporalMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProjectUsecaseImpl implements ProjectUsecase {
    private final MProjectService mProjectService;
    private final PProjectService pProjectService;

    /**
     * 프로젝트 생성
     * 1. MongoDB 프로젝트 생성
     * 2. PostgreSQL 프로젝트 등록 및 접속 권한 부여
     *
     * @param projectName projectName to make project
     * @param member      project maker
     * @return mongodb project objectId
     */
    @Transactional
    @Override
    public String createProject(CreateProjectDto createProjectDto, TemporalMember temporalMember) throws ParseException {
        var projectId = mProjectService.createProject(createProjectDto, temporalMember); // TODO: member security 적용
        pProjectService.registProject(projectId.toHexString(), createProjectDto, temporalMember); // TODO: member security 적용
        return projectId.toHexString();
    }

    @Override
    public ProjectInfoDto findProjectInfo(ObjectId projectId, TemporalMember member) { // TODO: security member
        var projectRoles = pProjectService.getProjectRoles(projectId, member);

        // 상위 권한 유저의 경우 키 정보 같이 조회
        if (projectRoles.contains(ProjectRole.MAINTAINER) || projectRoles.contains(ProjectRole.ADMIN)) {
            return mProjectService.findProjectInfoWithKey(projectId);
        }

        return mProjectService.findProjectInfo(projectId);
    }
}
