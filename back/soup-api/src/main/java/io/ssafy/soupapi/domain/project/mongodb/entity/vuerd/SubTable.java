package io.ssafy.soupapi.domain.project.mongodb.entity.vuerd;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubTable {
    @Field
    public String id;
    @Field
    public String name;
    @Field
    public String comment;
    @Field
    public List<Column> columns;
    @Field
    public Ui ui;
    @Field
    public boolean visible;
}
