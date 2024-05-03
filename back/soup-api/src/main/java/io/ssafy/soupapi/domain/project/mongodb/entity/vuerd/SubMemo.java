package io.ssafy.soupapi.domain.project.mongodb.entity.vuerd;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubMemo {
    @Field
    public String id;
    @Field
    public String value;
    @Field
    public Ui ui;
}
