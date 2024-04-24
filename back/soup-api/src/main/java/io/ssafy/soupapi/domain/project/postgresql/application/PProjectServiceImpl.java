package io.ssafy.soupapi.domain.project.postgresql.application;


import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.project.mongodb.entity.ProjectRole;
import io.ssafy.soupapi.domain.project.postgresql.dao.PProjectRepository;
import io.ssafy.soupapi.domain.project.postgresql.dto.response.SimpleProjectDto;
import io.ssafy.soupapi.domain.project.postgresql.entity.Project;
import io.ssafy.soupapi.domain.project.usecase.dto.request.CreateProjectDto;
import io.ssafy.soupapi.domain.projectauth.entity.ProjectAuth;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.OffsetPagination;
import io.ssafy.soupapi.global.common.response.PageOffsetResponse;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.security.TemporalMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class PProjectServiceImpl implements PProjectService {
    private final PProjectRepository pProjectRepository;

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
        pProjectRepository.save(project);
    }

    @Transactional(readOnly = true)
    @Override
    public PageOffsetResponse<List<SimpleProjectDto>> findSimpleProjects(PageOffsetRequest pageOffset, TemporalMember member) { // TODO: member security 적용
        var data = pProjectRepository.findSimpleProjectsByMemberId(
                member.getId(), PageOffsetRequest.of(pageOffset, Sort.by(Sort.Direction.DESC, "modifiedAt"))
        );
        return PageOffsetResponse.<List<SimpleProjectDto>>builder()
                .content(data.getContent())
                .pagination(OffsetPagination.offset(data.getTotalPages(), data.getTotalElements(), data.getPageable()))
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProjectRole> getProjectRoles(ObjectId projectId, TemporalMember member) { // TODO: security member
        List<ProjectAuth> response = pProjectRepository.findProjectAuthByProjectIdAndMemberId(projectId.toHexString(), member.getId());
        if (response.isEmpty()) {
            throw new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT_AUTH);
        }
        return response.stream().map(ProjectAuth::getRole).toList();
    }
}
