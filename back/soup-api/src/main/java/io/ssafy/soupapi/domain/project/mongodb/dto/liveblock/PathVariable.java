package io.ssafy.soupapi.domain.project.mongodb.dto.liveblock;

import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiVariable;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiVariableType;

public record PathVariable(
        String name,
        int type,
        String desc
) {
    public static ApiVariable toApiVariable(PathVariable pathVariable) {
        return ApiVariable.builder()
                .name(pathVariable.name())
                .type(pathVariable.getType())
                .description(pathVariable.desc())
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
