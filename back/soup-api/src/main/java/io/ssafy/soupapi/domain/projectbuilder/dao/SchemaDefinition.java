package io.ssafy.soupapi.domain.projectbuilder.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

import static io.ssafy.soupapi.domain.projectbuilder.dao.TableRelationDefinition.RelationType.MANY;
import static io.ssafy.soupapi.domain.projectbuilder.dao.TableRelationDefinition.RelationType.ONE;
import static io.ssafy.soupapi.global.util.StringParserUtil.*;

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

    public String getParentImportBuilder(String relationId, String domainPackage) {
        var relation = relations.get(relationId);
        var child = tables.get(relation.getChildTableId());

        return String.format("import %s.%s.entity.%s;",
                domainPackage, convertToSnakeCase(child.getName()), convertToPascalCase(child.getName()));
    }

    public String getChildImportBuilder(String relationId, String domainPackage) {
        var relation = relations.get(relationId);
        var parent = tables.get(relation.getParentTableId());

        return String.format("import %s.%s.entity.%s;",
                domainPackage, convertToSnakeCase(parent.getName()), convertToPascalCase(parent.getName())) +
               '\n';
    }

    public String getParentRelationBuilder(String relationId) {
        var relation = relations.get(relationId);
        StringBuilder sb = new StringBuilder();

        var parent = tables.get(relation.getParentTableId());
        var child = tables.get(relation.getChildTableId());

        if (relation.getRelationType() == ONE) {
            sb.append('\t').append(String.format(
                            "@OneToOne(mappedBy = \"%s\")", convertToSnakeCase(parent.getName())))
                    .append('\n');
            sb.append('\t').append(
                            String.format("private %s %s;", convertToPascalCase(child.getName()), convertToCamelCase(child.getName())))
                    .append('\n');
        } else if (relation.getRelationType() == MANY) {
            sb.append('\t').append(String.format("@OneToMany(mappedBy = \"%s\")",
                    convertToSnakeCase(parent.getName()))).append('\n');
            sb.append('\t').append(String.format("private List<%s> %s;",
                    convertToPascalCase(child.getName()), convertToCamelCase(child.getName()))).append('\n');
        }

        return sb.toString();
    }

    public String getChildRelationBuilder(String relationId) {
        var relation = relations.get(relationId);
        StringBuilder sb = new StringBuilder();

        var parent = tables.get(relation.getParentTableId());
        var child = tables.get(relation.getChildTableId());

        if (relation.getRelationType() == ONE) {
            sb.append('\t').append("@OneToOne").append('\n');
            sb.append('\t').append(String.format("@JoinColumn(name = \"%s\")",
                    parent.getTableIdName())).append('\n');
            sb.append('\t').append(
                            String.format("private %s %s;", convertToPascalCase(parent.getName()), convertToCamelCase(parent.getName())))
                    .append('\n');
        } else if (relation.getRelationType() == MANY) {
            sb.append('\t').append("@ManyToOne").append('\n');
            sb.append('\t').append(String.format("@JoinColumn(name=\"%s\")",
                    parent.getTableIdName())).append('\n');
            sb.append('\t').append(
                            String.format("private %s %s;", convertToPascalCase(parent.getName()), convertToCamelCase(parent.getName())))
                    .append('\n');
        }

        return sb.toString();
    }
}
