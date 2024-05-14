package io.ssafy.soupapi.domain.project.mongodb.dto.liveblock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiVariable;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiVariableType;

public record QueryParam(
        String name,
        int type,
        boolean required,
        String desc,
        @JsonProperty("default")
        String defaultValue
) {
    public static ApiVariable toApiVariable(QueryParam queryParam) {
        return ApiVariable.builder()
                .name(queryParam.name())
                .type(queryParam.getType())
                .description(queryParam.desc())
                .defaultVariable(queryParam.defaultValue())
                .require(queryParam.required())
                .build();
    }

    public ApiVariableType getType() {
        return switch (type) {
            case 1 -> ApiVariableType.String;
            case 2 -> ApiVariableType.Integer;
            case 3 -> ApiVariableType.Long;
            case 4 -> ApiVariableType.LocalDateTime;
            case 5 -> ApiVariableType.LocalDate;
            default -> ApiVariableType.Object;
        };
    }
}
