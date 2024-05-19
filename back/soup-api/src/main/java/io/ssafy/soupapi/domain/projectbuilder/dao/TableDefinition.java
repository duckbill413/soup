package io.ssafy.soupapi.domain.projectbuilder.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableDefinition {
    private String id;
    private String name;
    private String comment;
    @Builder.Default
    private List<String> columnIds = new ArrayList<>();
    @Builder.Default
    private List<String> seqColumnIds = new ArrayList<>();
    @Builder.Default
    private Map<String, ColumnDefinition> columns = new HashMap<>();

    public String getTableIdType() {
        for (ColumnDefinition c : columns.values()) {
            if (c.isId()) {
                return c.mapToJavaType();
            }
        }
        return "Object";
    }

    public String getTableIdName() {
        for (ColumnDefinition c : columns.values()) {
            if (c.isId()) {
                return c.getValidColumnName();
            }
        }
        return name + "_id";
    }
}