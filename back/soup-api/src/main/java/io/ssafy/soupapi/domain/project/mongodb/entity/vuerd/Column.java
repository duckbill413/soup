package io.ssafy.soupapi.domain.project.mongodb.entity.vuerd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Column{
    @Field
    public String id;
    @Field
    public String name;
    @Field
    public String comment;
    @Field
    public String dataType;
    @Field("default")
    @JsonProperty("default") 
    public String mydefault;
    @Field
    public Option option;
    @Field
    public Ui ui;
}
