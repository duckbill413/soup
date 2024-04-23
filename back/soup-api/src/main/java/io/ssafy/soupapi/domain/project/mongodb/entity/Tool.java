package io.ssafy.soupapi.domain.project.mongodb.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class Tool {
    @Field("name")
    private String toolName;
    @Field("url")
    private String url;
}
