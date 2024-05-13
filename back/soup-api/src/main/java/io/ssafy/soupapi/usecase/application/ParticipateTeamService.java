package io.ssafy.soupapi.usecase.application;

import com.google.gson.Gson;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.project.postgresql.dao.PProjectRepository;
import io.ssafy.soupapi.domain.project.postgresql.entity.Project;
import io.ssafy.soupapi.domain.project.postgresql.entity.ProjectRole;
import io.ssafy.soupapi.domain.projectauth.dao.ProjectAuthRepository;
import io.ssafy.soupapi.domain.projectauth.entity.ProjectAuth;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.external.liveblocks.application.LiveblocksComponent;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.ssafy.soupapi.global.util.FindEntityUtil;
import io.ssafy.soupapi.usecase.dao.TempTeamMember;
import io.ssafy.soupapi.usecase.dto.request.ParticipateTeam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ParticipateTeamService {
    private static final String TEMP_TEAM_MEMBER_HASH = "temp-team-member:";
    private final PProjectRepository projectRepository;
    private final ProjectAuthRepository projectAuthRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final Gson gson;
    private final LiveblocksComponent liveblocksComponent;
    private final FindEntityUtil findEntityUtil;

    public String participateToTeam(ParticipateTeam participateTeam, UserSecurityDTO userSecurityDTO) {
        var member = findEntityUtil.findMemberById(userSecurityDTO.getId());

        // 레디스 정보 호출
        var result = redisTemplate.opsForValue().get(TEMP_TEAM_MEMBER_HASH + member.getEmail());
        if (Objects.isNull(result)) {
            throw new BaseExceptionHandler(ErrorCode.INVALID_INVITE_CODE);
        }

        var tempTeamMember = gson.fromJson((String) result, TempTeamMember.class);

        // 코드 값이 다른 경우
        if (!tempTeamMember.code().equals(participateTeam.code())) {
            throw new BaseExceptionHandler(ErrorCode.INVALID_INVITE_CODE);
        }
        redisTemplate.opsForValue().getAndDelete(TEMP_TEAM_MEMBER_HASH + member.getEmail());
        var project = findEntityUtil.findPProjectById(tempTeamMember.projectId());

        addTeamMember(project, tempTeamMember.roles(), Member.builder().id(userSecurityDTO.getId()).build());
        liveblocksComponent.addMemberToAllStepRooms(userSecurityDTO.getId().toString(), project.getId());

        return project.getName() + " 프로젝트에 참가 되었습니다.";
    }

    private void addTeamMember(Project project, Set<ProjectRole> projectRoles, Member teamMember) {
        ProjectAuth projectAuth = ProjectAuth.builder()
                .roles(projectRoles)
                .member(teamMember)
                .project(project)
                .build();
        project.addProjectAuth(projectAuth);
        teamMember.addProjectAuth(projectAuth);
        projectAuthRepository.save(projectAuth);
    }
}
