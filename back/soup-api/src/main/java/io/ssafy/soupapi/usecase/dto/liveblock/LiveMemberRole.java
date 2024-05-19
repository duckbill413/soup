package io.ssafy.soupapi.usecase.dto.liveblock;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LiveMemberRole(
        @JsonProperty("role_id") String roleId,
        @JsonProperty("role_name") String roleName
) {
}
