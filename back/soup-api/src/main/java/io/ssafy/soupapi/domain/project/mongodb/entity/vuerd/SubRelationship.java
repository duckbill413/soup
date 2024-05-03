package io.ssafy.soupapi.domain.project.mongodb.entity.vuerd;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubRelationship {
    @Field
    public String id;
    @Field
    public boolean identification;
    @Field
    public String relationshipType;
    @Field
    public String startRelationshipType;
    @Field
    public Start start;
    @Field
    public End end;
    @Field
    public String constraintName;
    @Field
    public boolean visible;
}
