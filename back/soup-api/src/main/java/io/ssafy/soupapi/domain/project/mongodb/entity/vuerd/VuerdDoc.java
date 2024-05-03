package io.ssafy.soupapi.domain.project.mongodb.entity.vuerd;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VuerdDoc {
    @Field
    public Canvas canvas;
    @Field
    public Table table;
    @Field
    public Memo memo;
    @Field
    public Relationship relationship;
}
