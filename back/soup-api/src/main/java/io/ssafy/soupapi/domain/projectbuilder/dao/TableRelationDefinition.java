package io.ssafy.soupapi.domain.projectbuilder.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableRelationDefinition {
    private String id;
    private boolean identification;
    private int relationshipType;
    private int startRelationshipType;
    private Relation start;
    private Relation end;

    public RelationType getRelationType() {
        // 2: zeroOne, 8: oneOnly
        if ((relationshipType & 2) == 2 || (relationshipType & 8) == 8) {
            return RelationType.ONE;
        }
        // 4: zeroN, 16: oneN
        if ((relationshipType & 4) == 4 || (relationshipType & 16) == 16) {
            return RelationType.MANY;
        }
        throw new BaseExceptionHandler(ErrorCode.UNDEFINED_MASKING_CODE);
    }

    public String getParentTableId() {
        return start.getTableId();
    }

    public String getChildTableId() {
        return end.getTableId();
    }

    public enum RelationType {
        ONE, MANY
    }
}
