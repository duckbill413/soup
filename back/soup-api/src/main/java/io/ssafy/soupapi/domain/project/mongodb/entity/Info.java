package io.ssafy.soupapi.domain.project.mongodb.entity;

import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

public class Info {
    @Field("name")
    private String name;
    @Field("description")
    private String description;
    @Field("profile_img_url")
    private String profileImgUrl;
    @Field("start_date")
    private LocalDate startDate;
    @Field("end_date")
    private LocalDate endDate;
}
