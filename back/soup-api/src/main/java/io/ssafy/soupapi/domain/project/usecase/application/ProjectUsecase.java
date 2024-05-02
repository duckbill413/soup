package io.ssafy.soupapi.domain.project.usecase.application;

import io.ssafy.soupapi.global.security.user.UserSecurityDTO;

public interface ProjectUsecase {
    String createProject(UserSecurityDTO userSecurityDTO);
}
