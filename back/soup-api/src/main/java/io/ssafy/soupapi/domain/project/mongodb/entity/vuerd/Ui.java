package io.ssafy.soupapi.domain.project.mongodb.entity.vuerd;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Ui{
    @Field
    public boolean active;
    @Field
    public boolean pk;
    @Field
    public boolean fk;
    @Field
    public boolean pfk;
    @Field
    public double widthName;
    @Field
    public double widthComment;
    @Field
    public double widthDataType;
    @Field
    public int widthDefault;
    @Field
    public double left;
    @Field
    public double top;
    @Field
    public int zIndex;
    @Field
    public String color;
    @Field
    public int width;
    @Field
    public int height;
}
