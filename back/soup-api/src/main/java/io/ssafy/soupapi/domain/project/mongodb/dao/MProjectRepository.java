package io.ssafy.soupapi.domain.project.mongodb.dao;

import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MProjectRepository extends MongoRepository<Project, String> {
}
