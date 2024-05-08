package io.ssafy.soupapi.domain.project.mongodb.entity.apidocs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Builder
public class ApiVariable {
    @Field
    String name;
    @Field
    ApiVariableType type;
    @Field
    String description;
    @Field("default_variable")
    String defaultVariable;
    @Field
    boolean require;
}
