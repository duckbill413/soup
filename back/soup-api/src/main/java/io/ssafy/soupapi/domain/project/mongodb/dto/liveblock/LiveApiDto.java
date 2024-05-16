package io.ssafy.soupapi.domain.project.mongodb.dto.liveblock;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record LiveApiDto(
        String sort,
        @JsonProperty("list")
        List<LiveApiDetail> apiDetails
) {
}
