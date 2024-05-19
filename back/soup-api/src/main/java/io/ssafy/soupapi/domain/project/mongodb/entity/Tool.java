package io.ssafy.soupapi.domain.project.mongodb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
public class Tool {
    @Field("name")
    private String toolName;
    @Field("url")
    private String url;
}
