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
public class Setting{
    @Field
    public boolean relationshipDataTypeSync;
    @Field
    public boolean relationshipOptimization;
    @Field
    public List<String> columnOrder;
}
