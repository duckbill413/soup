package io.ssafy.soupapi.domain.project.postgresql.dao;

import io.ssafy.soupapi.domain.project.postgresql.dto.response.SimpleProjectDto;
import io.ssafy.soupapi.domain.project.postgresql.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PProjectRepository extends JpaRepository<Project, String> {
    @Query(value = """
            SELECT new io.ssafy.soupapi.domain.project.postgresql.dto.response.SimpleProjectDto(p.id, p.name, p.imgUrl)
            FROM Project p
            JOIN ProjectAuth pa ON p = pa.project
            JOIN Member m ON pa.member = m
            WHERE m.id = :memberId
            """)
    Page<SimpleProjectDto> findSimpleProjectsByMemberId(@Param("memberId") UUID id, Pageable pageable);
}
