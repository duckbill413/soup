package io.ssafy.soupapi.domain.project.mongodb.entity.issue;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ProjectUser {
    @Field("project_member_id")
    UUID memberId;
    @Field("jira_id")
    String id;
    @Field("jira_email")
    String email;
    @Field("jira_name")
    String name;
}
