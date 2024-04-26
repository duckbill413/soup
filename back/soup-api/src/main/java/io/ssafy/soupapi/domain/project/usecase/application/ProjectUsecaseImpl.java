package io.ssafy.soupapi.domain.project.usecase.application;

import io.ssafy.soupapi.domain.member.dao.MemberRepository;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.project.mongodb.application.MProjectService;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectProposal;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectProposal;
import io.ssafy.soupapi.domain.project.postgresql.application.PProjectService;
import io.ssafy.soupapi.domain.project.postgresql.entity.ProjectRole;
import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.domain.project.usecase.dto.request.InviteTeammate;
import io.ssafy.soupapi.domain.projectauth.dao.ProjectAuthRepository;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.security.TemporalMember;
import io.ssafy.soupapi.global.util.GmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProjectUsecaseImpl implements ProjectUsecase {
    private final MProjectService mProjectService;
    private final PProjectService pProjectService;
    private final MemberRepository memberRepository;
    private final ProjectAuthRepository projectAuthRepository;
    private final GmailUtil gmailUtil;

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
    public String createProject(CreateProjectDto createProjectDto, TemporalMember temporalMember) {
        var projectId = mProjectService.createProject(createProjectDto, temporalMember); // TODO: member security 적용
        pProjectService.registProject(projectId.toHexString(), createProjectDto, temporalMember); // TODO: member security 적용
        return projectId.toHexString();
    }

    /**
     * 프로젝트 Info 데이터 조회
     *
     * @param projectId 조회할 mongodb project projectId
     * @param member    조회하는 member
     * @return GetProjectInfo
     */
    @Override
    public GetProjectInfo findProjectInfo(String projectId, TemporalMember member) { // TODO: security member
        return mProjectService.findProjectInfoAndTools(new ObjectId(projectId));
    }

    /**
     * 프로젝트 Info 데이터 조회
     *
     * @param projectId 조회할 mongodb project projectId
     * @param member    조회하는 member
     * @return ProjectInfoDto
     */
    @Override
    public GetProjectProposal findProjectProposal(String projectId, TemporalMember member) {
        var project = pProjectService.findById(projectId);
        // 최초 초대된 멤버인지 확인
        var projectAuth = projectAuthRepository.findByMemberAndProject(
                Member.builder().id(member.getId()).build(),
                project); // TODO: security member

        // mongodb에 있는지 확인하고 없으면 에러, 있으면 postgre에 추가
        if (projectAuth.isEmpty()) {
            var inviteTeammateDto = InviteTeammate.builder()
                    .projectId(projectId)
                    .email(member.getEmail())
                    .roles(roles)
                    .build();

            pProjectService.addTeammate(inviteTeammateDto, Member.builder().id(member.getId()).build(), project);
        }
        // 프로젝트 권한 검사
        pProjectService.getProjectRoles(projectId, member);
        // 프로젝트 제안서 데이터 조회
        return mProjectService.findProjectProposal(new ObjectId(projectId));
    }

    @Override
    public GetProjectProposal updateProjectProposal(String projectId, UpdateProjectProposal updateProjectProposal, TemporalMember member) {
        // 프로젝트 제안서 수정 권한 검증
        var roles = pProjectService.getProjectRoles(projectId, member);
        if (roles.contains(ProjectRole.VIEWER)) {
            throw new BaseExceptionHandler(ErrorCode.FAILED_TO_UPDATE_PROJECT);
        }
        // 프로젝트 제안서 수정
        return mProjectService.updateProjectProposal(new ObjectId(projectId), updateProjectProposal);
    }

    /**
     * 프로젝트 정보 업데이트
     *
     * @param projectId         프로젝트 ID
     * @param updateProjectInfo 업데이트할 프로젝트 정보
     * @param member            업데이트 하는 멤버
     * @return
     */
    @Override
    public GetProjectInfo updateProjectInfo(String projectId, UpdateProjectInfo updateProjectInfo, TemporalMember member) {
        // 프로젝트 권한 검사
        var roles = pProjectService.getProjectRoles(projectId, member);
        if (roles.contains(ProjectRole.VIEWER)) {
            throw new BaseExceptionHandler(ErrorCode.FAILED_TO_UPDATE_PROJECT);
        }

        return mProjectService.updateProjectInfo(new ObjectId(projectId), updateProjectInfo); // TODO: security member
    }

    /**
     * 프로젝트 지라 키정보 조회
     *
     * @param projectId 프로젝트 ID
     * @param member    키정보를 조회하는 멤버
     * @return 지라 유저 이름 및 키 값
     */
    @Override
    public GetProjectJiraKey findProjectJiraKey(String projectId, TemporalMember member) {
        var roles = pProjectService.getProjectRoles(projectId, member);
        if (roles.contains(ProjectRole.ADMIN) || roles.contains(ProjectRole.MAINTAINER)) {
            return mProjectService.findProjectJiraKey(new ObjectId(projectId));
        }
        throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT_AUTH);
    }

    @Override
    public GetProjectJiraKey updateProjectJiraKey(String projectId, UpdateProjectJiraKey updateProjectJiraKey, TemporalMember member) {
        var roles = pProjectService.getProjectRoles(projectId, member);
        if (roles.contains(ProjectRole.ADMIN) || roles.contains(ProjectRole.MAINTAINER)) {
            return mProjectService.updateProjectJiraKey(new ObjectId(projectId), updateProjectJiraKey);
        }
        throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT_AUTH);
    }
}
