package io.ssafy.soupapi.global.util;

import io.ssafy.soupapi.domain.member.dao.MemberRepository;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.project.postgresql.entity.Project;
import io.ssafy.soupapi.domain.project.postgresql.entity.ProjectRole;
import io.ssafy.soupapi.domain.projectauth.dao.ProjectAuthRepository;
import io.ssafy.soupapi.domain.projectauth.entity.ProjectAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final ProjectAuthRepository projectAuthRepository;
    private static final ProjectRole[] PRIMARY_PROJECT_ROLE = {ProjectRole.ADMIN, ProjectRole.MAINTAINER};
    private static final ProjectRole VIEWER_PROJECT_ROLE = ProjectRole.VIEWER;

    /**
     * 검색한 프로젝트에 대해 어떠한 권한을 가지고 있는지 확인
     *
     * @param memberId
     * @param projectId
     * @return
     */
    public boolean hasProjectRoleMember(String projectId, UUID memberId) {
        var projectAuths = projectAuthRepository.findByMemberAndProject(
                Member.builder().id(memberId).build(),
                Project.builder().id(projectId).build()
        );
        return !projectAuths.isEmpty();
    }

    public boolean hasPrimaryProjectRoleMember(String projectId, UUID memberId) {
        var projectAuths = projectAuthRepository.findByMemberAndProject(
                Member.builder().id(memberId).build(),
                Project.builder().id(projectId).build()
        );

        for (ProjectAuth projectAuth : projectAuths) {
            for (ProjectRole projectRole : PRIMARY_PROJECT_ROLE) {
                if (projectAuth.getRoles().contains(projectRole)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasViewerProjectRoleMember(String projectId, UUID memberId) {
        var projectAuths = projectAuthRepository.findByMemberAndProject(
                Member.builder().id(memberId).build(),
                Project.builder().id(projectId).build()
        );

        for (ProjectAuth projectAuth : projectAuths) {
            if (projectAuth.getRoles().contains(VIEWER_PROJECT_ROLE)) {
                return true;
            }
        }
        return false;
    }


    public boolean hasChatProjectRoleMember(String projectId, UUID memberId) {
        var projectAuths = projectAuthRepository.findByMemberAndProject(
                Member.builder().id(memberId).build(),
                Project.builder().id(projectId).build()
        );

        for (ProjectAuth projectAuth : projectAuths) {
            if (projectAuth.isStatus()) {
                return true;
            }
        }
        return false;
    }

}
