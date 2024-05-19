package io.ssafy.soupapi.domain.readme.dao;

import io.ssafy.soupapi.domain.readme.entity.BasicTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadmeRepository extends MongoRepository<BasicTemplate, String> {
    BasicTemplate findByTitle(String title);

}