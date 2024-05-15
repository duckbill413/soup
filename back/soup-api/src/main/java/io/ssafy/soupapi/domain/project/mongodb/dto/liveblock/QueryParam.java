package io.ssafy.soupapi.domain.project.mongodb.dto.liveblock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiVariable;
import io.ssafy.soupapi.domain.project.mongodb.entity.apidocs.ApiVariableType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QueryParam(
        @NotBlank(message = "name은 필수입니다.")
        String name,
        @Size(max = 5, message = "type은 0~5 사이입니다.")
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
