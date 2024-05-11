package io.ssafy.soupapi.domain.project.mongodb.entity;

import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiDocs;
import io.ssafy.soupapi.domain.project.mongodb.entity.issue.ProjectIssue;
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
    @Field("project_proposal")
    private Proposal proposal;
    @Builder.Default
    @Field("project_issues")
    private List<ProjectIssue> issues = new ArrayList<>();
    @Field("project_vuerd")
    private Object vuerd;
    @Field("project_api_doc")
    private ApiDocs apiDocs;
    @Builder.Default
    @Field("project_chats")
    private List<ChatMessage> chats = new ArrayList<>();
}
