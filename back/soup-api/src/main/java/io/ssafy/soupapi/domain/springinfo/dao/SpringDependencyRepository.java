package io.ssafy.soupapi.domain.springinfo.dao;

import io.ssafy.soupapi.domain.springinfo.entity.Dependency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDependencyRepository extends JpaRepository<Dependency, Long> {
    List<Dependency> findByIdIsInOrBasicIsTrue(List<Long> ids);
}
