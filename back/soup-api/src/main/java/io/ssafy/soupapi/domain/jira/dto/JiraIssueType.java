
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
public class JiraIssueType {

    @Builder.Default
    public List<Issuetype> issueTypes = new ArrayList<>();
    public Long maxResults;
    public Long startAt;
    public Long total;

}
