package io.ssafy.soupapi.domain.project.postgresql.dao;

import io.ssafy.soupapi.domain.project.postgresql.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> {
}
