package io.ssafy.soupapi.domain.project.mongodb.entity.apidocs;

import lombok.Getter;

@Getter
public class ApiVariable {
    private String name;
    private ApiVariableType type;
    private String description;
    private String defaultVariable;
    private boolean require;
}
