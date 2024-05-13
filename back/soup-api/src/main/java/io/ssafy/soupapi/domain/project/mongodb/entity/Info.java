package io.ssafy.soupapi.domain.project.mongodb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Info {
    @Field("project_name")
    private String name;
    @Field("project_description")
    private String description;
    @Field("project_img_url")
    private String imgUrl;
    @Field("project_start_date")
    private Instant startDate;
    @Field("project_end_date")
    private Instant endDate;
    @Field("project_jira_host")
    private String jiraHost;
    @Field("project_jira_project_key")
    private String jiraProjectKey;
    @Field("project_jira_username")
    private String jiraUsername;
    @Field("project_jira_key")
    private String jiraKey;
}
