package io.ssafy.soupapi.domain.project.mongodb.entity;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

public class TeamMember {
    @Field("id")
    private UUID id;
    @Field("email")
    private String email;
    @Field("roles")
    private List<String> roles;
}
