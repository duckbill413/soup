package io.ssafy.soupapi.domain.projectbuilder.dao;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class SchemaDefinition {
    @Builder.Default
    Map<String, TableDefinition> tables = new HashMap<>();
    @Builder.Default
    Map<String, TableRelationDefinition> relations = new HashMap<>();
}
