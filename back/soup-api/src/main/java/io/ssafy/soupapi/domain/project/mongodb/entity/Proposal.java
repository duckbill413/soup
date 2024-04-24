package io.ssafy.soupapi.domain.project.mongodb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
public class Proposal {
    @Field("background")
    private String background;
    @Field("introduce")
    private String introduce;
    @Field("target")
    private String target;
    @Field("expectation")
    private String expectation;
}
