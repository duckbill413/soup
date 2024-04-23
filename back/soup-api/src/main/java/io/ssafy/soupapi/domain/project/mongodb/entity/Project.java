package io.ssafy.soupapi.domain.project.mongodb.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@Builder
@Document(collation = "projects")
public class Project {
    @Id
    @Field("project_id")
    private String id;
    @Field("project_info")
    private Info info;
    @Field("project_tools")
    private List<Tool> tools;
    @Field("project_team_members")
    private List<TeamMember> teamMembers;
    private Proposal proposal;
}
