package io.ssafy.soupapi.domain.projectauth.application;

import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.project.postgresql.entity.Project;
import io.ssafy.soupapi.domain.project.postgresql.entity.ProjectRole;
import io.ssafy.soupapi.domain.project.usecase.dto.request.InviteTeamMember;
import io.ssafy.soupapi.domain.projectauth.dto.response.GetProjectTeamMember;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface ProjectAuthService {
    List<GetProjectTeamMember> findProjectTeamMembers(String projectId);
}
