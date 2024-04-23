package io.ssafy.soupapi.domain.project.mongodb.application;

import io.ssafy.soupapi.domain.project.mongodb.dao.MProjectRepository;
import io.ssafy.soupapi.domain.project.mongodb.entity.Info;
import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.ProjectRole;
import io.ssafy.soupapi.domain.project.mongodb.entity.TeamMember;
import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.global.security.TemporalMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class MProjectServiceImpl implements MProjectService {
    private final MProjectRepository MProjectRepository;

    /**
     * 프로젝트 생성 및 최초 팀 구성 설정
     * 최초 생성자 권한을 ADMIN으로 지정
     *
     * @param createProjectDto new project's project data
     * @param member           project maker
     * @return mongodb project object id
     */
    @Transactional
    @Override
    public ObjectId createProject(CreateProjectDto createProjectDto, TemporalMember temporalMember) { // TODO: member security 적용
        // 프로젝트 및 프로젝트 이름 설정
        var project = Project.builder()
                .info(
                        Info.builder()
                                .name(createProjectDto.name())
                                .profileImgUrl(createProjectDto.imgUrl())
                                .build()
                ).build();
        // 프로젝트 팀 구성 설정 (프로젝트 생성자에게 ADMIN 권한 부여)
        project.getTeamMembers().add(
                TeamMember.builder()
                        .id(temporalMember.getId()) // TODO: member security 적용
                        .email(temporalMember.getEmail()) // TODO: member security 적용
                        .roles(List.of(ProjectRole.ADMIN))
                        .build()
        );

        return MProjectRepository.save(project).getId();
    }
}
