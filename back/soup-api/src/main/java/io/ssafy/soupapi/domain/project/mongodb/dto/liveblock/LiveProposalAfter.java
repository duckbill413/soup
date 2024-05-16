package io.ssafy.soupapi.domain.project.mongodb.dto.liveblock;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LiveProposalAfter(
        @JsonProperty("project_background")
        String background,
        @JsonProperty("project_intro")
        String intro,
        @JsonProperty("project_target")
        String target,
        @JsonProperty("project_effect")
        String effect
) {
}
