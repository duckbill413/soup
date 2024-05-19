package io.ssafy.soupapi.domain.project.mongodb.entity.builder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
public class ProjectBuilderDependency {
    @Field("dependency_id")
    Long id;
    @Field("dependency_name")
    String name;
    @Field("dependency_description")
    String description;
    @Field("dependency_category")
    String category;
    @Field("dependency_code")
    String code;
    @Field("basic_dependency")
    boolean basic;
}
