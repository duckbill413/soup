package io.ssafy.soupapi.domain.jira.dto.response;

public enum JiraPriority {
    LOWEST(5),
    LOW(4),
    MEDIUM(3),
    HIGH(2),
    HIGHEST(1),
    ;

    final int jiraId;

    JiraPriority(int jiraId) {
        this.jiraId = jiraId;
    }
}
