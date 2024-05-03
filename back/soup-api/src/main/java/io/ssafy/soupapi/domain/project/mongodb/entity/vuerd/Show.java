package io.ssafy.soupapi.domain.project.mongodb.entity.vuerd;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Show{
    @Field
    public boolean tableComment;
    @Field
    public boolean columnComment;
    @Field
    public boolean columnDataType;
    @Field
    public boolean columnDefault;
    @Field
    public boolean columnAutoIncrement;
    @Field
    public boolean columnPrimaryKey;
    @Field
    public boolean columnUnique;
    @Field
    public boolean columnNotNull;
    @Field
    public boolean relationship;
}
