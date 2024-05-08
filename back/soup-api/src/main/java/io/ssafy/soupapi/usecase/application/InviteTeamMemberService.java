package io.ssafy.soupapi.usecase.application;

import com.google.gson.Gson;
import io.ssafy.soupapi.domain.member.dao.MemberRepository;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.project.postgresql.dao.PProjectRepository;
import io.ssafy.soupapi.domain.project.postgresql.entity.Project;
import io.ssafy.soupapi.domain.project.postgresql.entity.ProjectRole;
import io.ssafy.soupapi.domain.project.usecase.dto.request.InviteTeamMember;
import io.ssafy.soupapi.domain.projectauth.dao.ProjectAuthRepository;
import io.ssafy.soupapi.domain.projectauth.entity.ProjectAuth;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.external.liveblocks.application.LiveblocksComponent;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.ssafy.soupapi.global.util.FindEntityUtil;
import io.ssafy.soupapi.global.util.GmailUtil;
import io.ssafy.soupapi.usecase.dao.TempTeamMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
@RequiredArgsConstructor
public class InviteTeamMemberService {
    private static final String TEMP_TEAM_MEMBER_HASH = "temp-team-member:";
    private final MemberRepository memberRepository;
    private final PProjectRepository projectRepository;
    private final ProjectAuthRepository projectAuthRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final Gson gson;
    private final GmailUtil gmailUtil;
    private final LiveblocksComponent liveblocksComponent;
    private final FindEntityUtil findEntityUtil;

    @Transactional
    public String inviteTeamMember(String projectId, InviteTeamMember inviteTeamMember, UserSecurityDTO userSecurityDTO) {
        var member = findEntityUtil.findMemberById(userSecurityDTO.getId());
        var project = findEntityUtil.findPProjectById(projectId);
        var invitee = memberRepository.findByEmail(inviteTeamMember.email()).stream().findFirst().orElse(null);

        // 초대하려는 멤버가 이미 우리 프로젝트 팀원 인지 확인
        var projectAuths = projectAuthRepository.findByMemberAndProject(invitee, project);
        if (!projectAuths.isEmpty()) {
            throw new BaseExceptionHandler(ErrorCode.ALREADY_EXISTS_PROJECT_MEMBER);
        }

        if (!validInviteRole(inviteTeamMember.roles(), project, userSecurityDTO)) {
            throw new BaseExceptionHandler(ErrorCode.INVALID_INVITE_PROJECT_ROLE);
        }

        // 이메일에 해당하는 회원이 없는 경우 (아직 미가입)
        if (Objects.isNull(invitee)) {
            String idempotent = generateInviteCode(); // 멱등키 발급
            TempTeamMember tempTeamMember = TempTeamMember.builder()
                    .projectId(projectId)
                    .roles(inviteTeamMember.roles())
                    .code(idempotent)
                    .build();

            // 레디스에 임시 유저 정보 저장
            redisTemplate.opsForValue().set(
                    TEMP_TEAM_MEMBER_HASH + inviteTeamMember.email(),
                    gson.toJson(tempTeamMember),
                    10, TimeUnit.MINUTES);

            // Gmail 초대 메일 발송
            try {
                gmailUtil.sendMail(
                        inviteTeamMember.email(),
                        Objects.nonNull(member.getEmail()) ? member.getEmail() : project.getName().replaceAll(" ", ""),
                        project.getName() + " 프로젝트에서 초대",
                        getEmailFormat(project, idempotent),
                        true
                );
            } catch (Exception e) {
                throw new BaseExceptionHandler(ErrorCode.FAILED_TO_SEND_GMAIL, e.getMessage());
            }

            return "프로젝트 초대 이메일 발송";
        }

        // 현재 회원인 경우
        addTeamMember(project, inviteTeamMember.roles(), invitee);
        liveblocksComponent.addMemberToAllStepRooms(invitee.getId().toString(), project.getId());

        return invitee.getNickname() + "님 초대 완료";
    }

    /**
     * 부여할 수 있는 권한 인지 확인
     *
     * @param roles           부여할 권한 목록
     * @param project         프로젝트
     * @param userSecurityDTO 권한을 부여하는 유저
     * @return 권한 부여 가능 여부
     */
    private boolean validInviteRole(Set<ProjectRole> roles, Project project, UserSecurityDTO userSecurityDTO) {
        List<ProjectAuth> projectAuths = projectAuthRepository.findByMemberAndProject(
                Member.builder().id(userSecurityDTO.getId()).build(), project
        );
        Set<ProjectRole> myRoles = projectAuths.stream().findFirst().orElseThrow(
                () -> new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT_AUTH)
        ).getRoles();
        if (myRoles.contains(ProjectRole.ADMIN)) {
            return true;
        }
        if (myRoles.contains(ProjectRole.MAINTAINER)) {
            return !roles.contains(ProjectRole.ADMIN);
        }
        return true;
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

    private static String getEmailFormat(Project project, String idempotent) {
        String htmlTemplate = """
                <!DOCTYPE html>
                <html lang="ko">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>프로젝트 초대</title>
                </head>
                <body style="font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0;">
                    <div style="max-width: 600px; margin: 20px auto; padding: 20px; background-color: #ffffff; border-radius: 5px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);">
                        <h1 style="color: #333;">%s 프로젝트 초대</h1>
                        <p style="font-size: 16px; line-height: 1.5;">안녕하세요! 저희 프로젝트에 초대합니다.</p>
                        <div style="text-align: center;">
                            <img style="display: block; margin: 0 auto; max-width: 100%%; height: auto; margin-bottom: 20px; margin-top: 20px" src="%s" alt="프로젝트 대표 이미지">
                        </div>
                        <p style="font-size: 16px; line-height: 1.5;">아래의 초대 코드를 사용하여 프로젝트에 참여하세요:</p>
                        <div style="text-align: center;">
                            <div style="font-size: 24px; font-weight: bold; color: #333; background-color: #f0f0f0; padding: 10px; border-radius: 5px; display: inline-block; margin-top: 20px;">%s</div>
                        </div>
                        <hr style="border: none; border-top: 1px solid #ccc; margin: 20px 0;">
                        <div style="max-width: 600px; margin: 0 auto; text-align: center; font-size: 16px; color: #777676;">
                            <p style="margin: 0;"><a href="https://so-up.store" style="color: #777676; text-decoration: none;">https://so-up.store</a></p>
                            <p style="margin: 0;">Soup</p>
                        </div>
                    </div>
                </body>
                </html>
                """;

        return String.format(htmlTemplate, project.getName(), project.getImgUrl(), idempotent);
    }

    public static String generateInviteCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = 10;

        StringBuilder sb = new StringBuilder(length);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }

        return sb.toString();
    }
}
