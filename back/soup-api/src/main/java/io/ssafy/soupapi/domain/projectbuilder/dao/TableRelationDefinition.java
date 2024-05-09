package io.ssafy.soupapi.domain.projectbuilder.dao;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class TableRelationDefinition {
    public String id;
    public boolean identification;
    public int relationshipType;
    public int startRelationshipType;
    public Relation start;
    public Relation end;
}
