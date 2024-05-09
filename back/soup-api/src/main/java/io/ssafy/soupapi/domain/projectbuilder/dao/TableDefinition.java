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
}