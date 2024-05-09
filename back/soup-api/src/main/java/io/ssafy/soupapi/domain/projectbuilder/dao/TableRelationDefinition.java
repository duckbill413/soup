package io.ssafy.soupapi.domain.projectbuilder.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableRelationDefinition {
    private String id;
    private boolean identification;
    private int relationshipType;
    private int startRelationshipType;
    private Relation start;
    private Relation end;
}
