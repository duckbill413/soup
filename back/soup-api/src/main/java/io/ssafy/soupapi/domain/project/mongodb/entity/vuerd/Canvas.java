package io.ssafy.soupapi.domain.project.mongodb.entity.vuerd;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Canvas {
    @Field
    public String version;
    @Field
    public int width;
    @Field
    public int height;
    @Field
    public int scrollTop;
    @Field
    public int scrollLeft;
    @Field
    public double zoomLevel;
    @Field
    public Show show;
    @Field
    public String database;
    @Field
    public String databaseName;
    @Field
    public String canvasType;
    @Field
    public String language;
    @Field
    public String tableCase;
    @Field
    public String columnCase;
    @Field
    public String highlightTheme;
    @Field
    public String bracketType;
    @Field
    public Setting setting;
    @Field
    public Map<String, Object> pluginSerializationMap;
}
