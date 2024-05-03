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
public class Start {
    @Field
    public String tableId;
    @Field
    public List<String> columnIds;
    @Field
    public double x;
    @Field
    public double y;
    @Field
    public String direction;
}
