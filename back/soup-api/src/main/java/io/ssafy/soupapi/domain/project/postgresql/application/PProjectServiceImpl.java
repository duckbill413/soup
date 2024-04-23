package io.ssafy.soupapi.domain.project.postgresql.application;


import io.ssafy.soupapi.domain.member.Member;
import io.ssafy.soupapi.domain.project.mongodb.entity.ProjectRole;
import io.ssafy.soupapi.domain.project.postgresql.dao.PProjectRepository;
import io.ssafy.soupapi.domain.project.postgresql.entity.Project;
import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.domain.projectauth.ProjectAuth;
import io.ssafy.soupapi.global.security.TemporalMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class PProjectServiceImpl implements PProjectService {
    private final PProjectRepository PProjectRepository;

    /**
     * mongodb에서 생성된 프로젝트를 postgresql project 객체로 연관 등록
     * project auth 등록
     *
     * @param projectId        new mongodb project's project id
     * @param createProjectDto new project's project data
     * @param member           new project maker
     */
    @Override
    public void registProject(String projectId, CreateProjectDto createProjectDto, TemporalMember temporalMember) { // TODO: member security 적용
        // 프로젝트 등록
        var project = Project.builder()
                .id(projectId)
                .name(createProjectDto.name())
                .imgUrl(createProjectDto.imgUrl())
                .build();
        // 프로젝트 최초 권한 등록
        project.addProjectAuth(ProjectAuth.builder()
                .role(ProjectRole.ADMIN)
                .member(Member.builder().id(temporalMember.getId()).build()) // TODO: member security 적용
                .project(project)
                .build());
        PProjectRepository.save(project);
    }
}
