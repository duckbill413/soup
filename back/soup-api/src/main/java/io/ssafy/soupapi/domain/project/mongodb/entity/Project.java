package io.ssafy.soupapi.domain.project.mongodb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Document(collection = "projects")
public class Project {
    @Id
    @Field("project_id")
    private ObjectId id;
    @Field("project_info")
    private Info info;
    @Builder.Default
    @Field("project_tools")
    private List<Tool> tools = new ArrayList<>();
    @Builder.Default
    @Field("project_team_members")
    private List<TeamMember> teamMembers = new ArrayList<>();
    @Field("project_proposal")
    private Proposal proposal;
}
