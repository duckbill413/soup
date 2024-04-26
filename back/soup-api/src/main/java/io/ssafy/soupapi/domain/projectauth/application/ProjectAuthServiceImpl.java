package io.ssafy.soupapi.domain.projectauth.application;

import io.ssafy.soupapi.domain.projectauth.dao.ProjectAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProjectAuthServiceImpl implements ProjectAuthService {
    private final ProjectAuthRepository projectAuthRepository;

}
