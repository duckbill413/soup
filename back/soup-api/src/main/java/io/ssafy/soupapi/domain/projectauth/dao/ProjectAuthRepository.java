package io.ssafy.soupapi.domain.projectauth.dao;

import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.project.postgresql.entity.Project;
import io.ssafy.soupapi.domain.projectauth.entity.ProjectAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectAuthRepository extends JpaRepository<ProjectAuth, UUID> {
    List<ProjectAuth> findByMemberAndProject(Member member, Project project);

    List<ProjectAuth> findByProject_Id(String projectId);
}
