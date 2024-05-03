package io.ssafy.soupapi.domain.project.mongodb.entity.vuerd;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Option{
    @Field
    public boolean autoIncrement;
    @Field
    public boolean primaryKey;
    @Field
    public boolean unique;
    @Field
    public boolean notNull;
}
