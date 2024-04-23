package io.ssafy.soupapi.domain.project.mongodb.entity;

import org.springframework.data.mongodb.core.mapping.Field;

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
