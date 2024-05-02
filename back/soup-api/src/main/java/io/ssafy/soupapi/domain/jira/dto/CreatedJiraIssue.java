package io.ssafy.soupapi.domain.jira.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreatedJiraIssue {
    private String id;
    private String key;
    private String self;
    private CreatedTransition transition;
}
