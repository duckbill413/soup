package io.ssafy.soupapi.domain.projectauth.application;

import io.ssafy.soupapi.domain.projectauth.dao.ProjectAuthRepository;
import io.ssafy.soupapi.domain.projectauth.dto.response.GetProjectAccessInfo;
import io.ssafy.soupapi.domain.projectauth.dto.response.GetProjectTeamMember;
import io.ssafy.soupapi.domain.projectauth.entity.ProjectAuth;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProjectAuthServiceImpl implements ProjectAuthService {

    private final ProjectAuthRepository projectAuthRepository;

    @Transactional
    @Override
    public List<GetProjectTeamMember> findProjectTeamMembers(String projectId) {
        var projectAuths = projectAuthRepository.findByProject_Id(projectId);
        if (projectAuths.isEmpty()) {
            throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT_AUTH);
        }

        List<GetProjectTeamMember> teamMembers = new ArrayList<>();
        for (ProjectAuth projectAuth : projectAuths) {
            teamMembers.add(GetProjectTeamMember.builder()
                    .id(projectAuth.getMember().getId())
                    .email(projectAuth.getMember().getEmail())
                    .nickname(projectAuth.getMember().getNickname())
                    .profileImageUrl(projectAuth.getMember().getProfileImageUrl())
                    .phone(projectAuth.getMember().getPhone())
                    .roles(projectAuth.getRoles().stream().toList())
                    .build());
        }

        return teamMembers;
    }

    @Transactional(readOnly = true)
    @Override
    public GetProjectAccessInfo checkProjectAccessInfo(String projectId, UserSecurityDTO userSecurityDTO) {
        var projectAuth = projectAuthRepository.findByProject_Id(projectId).stream().findFirst().orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT_AUTH));
        return new GetProjectAccessInfo(projectAuth.getRoles());
    }
}
