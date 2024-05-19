package io.ssafy.soupapi.domain.jira.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreatedTransition {
    private Long status;
}
