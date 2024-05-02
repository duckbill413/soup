package io.ssafy.soupapi.domain.jira.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreatedJiraIssues {
    private List<CreatedJiraIssue> issues;
    private List<Object> errors;
}
