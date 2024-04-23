package io.ssafy.soupapi.domain.project.mongodb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class TeamMember {
    @Field("id")
    private UUID id;
    @Field("email")
    private String email;
    @Builder.Default
    @Field("roles")
    private List<ProjectRole> roles = new ArrayList<>();
}
