package io.ssafy.soupapi.domain.projectbuilder.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

import static io.ssafy.soupapi.domain.projectbuilder.dao.TableRelationDefinition.RelationType.MANY;
import static io.ssafy.soupapi.domain.projectbuilder.dao.TableRelationDefinition.RelationType.ONE;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchemaDefinition {
    @Builder.Default
    private Map<String, TableDefinition> tables = new HashMap<>();
    @Builder.Default
    private Map<String, TableRelationDefinition> relations = new HashMap<>();

    public String getParentAnnotation(String relationId) {
        var relation = relations.get(relationId);
        StringBuilder sb = new StringBuilder();

        if (relation.getRelationType() == ONE) {

        } else if (relation.getRelationType() == MANY) {

        }
        return null;
    }

    public String getChildAnnotation(String relationId) {
        return null;
    }
}
