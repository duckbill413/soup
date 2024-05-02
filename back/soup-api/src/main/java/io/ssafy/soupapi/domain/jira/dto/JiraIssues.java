
package io.ssafy.soupapi.domain.jira.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JiraIssues {

    public String expand;
    public Long startAt;
    public Long maxResults;
    public Long total;
    @Builder.Default
    public List<Issue> issues = new ArrayList<>();

}
