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
    public String id;
    public boolean identification;
    public int relationshipType;
    public int startRelationshipType;
    public Relation start;
    public Relation end;
}
