package io.ssafy.soupapi.domain.springinfo.dao;

import io.ssafy.soupapi.domain.springinfo.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringVersionRepository extends JpaRepository<Version, Long> {
}
