package io.ssafy.soupapi.domain.projectauth.application;

import io.ssafy.soupapi.domain.projectauth.dto.response.GetProjectAccessInfo;
import io.ssafy.soupapi.domain.projectauth.dto.response.GetProjectTeamMember;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;

import java.util.List;

public interface ProjectAuthService {
    List<GetProjectTeamMember> findProjectTeamMembers(String projectId);

    GetProjectAccessInfo checkProjectAccessInfo(String projectId, UserSecurityDTO userSecurityDTO);
}
