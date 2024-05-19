package io.ssafy.soupapi.domain.project.mongodb.dto.liveblock;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record LiveProposalBefore(
        @JsonProperty("project_using")
        boolean using,
        @JsonProperty("project_background")
        List<LiveProposalTag> backgroundTags,
        @JsonProperty("project_intro")
        List<LiveProposalTag> introTags,
        @JsonProperty("project_target")
        List<LiveProposalTag> targetTags,
        @JsonProperty("project_effect")
        List<LiveProposalTag> effectTags
) {
}
