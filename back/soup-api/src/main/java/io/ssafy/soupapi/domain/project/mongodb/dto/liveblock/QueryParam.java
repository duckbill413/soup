package io.ssafy.soupapi.domain.project.mongodb.dto.liveblock;

import com.fasterxml.jackson.annotation.JsonProperty;

public record QueryParam(
        String name,
        int type,
        boolean required,
        String desc,
        @JsonProperty("default")
        String defaultValue
) {
}
