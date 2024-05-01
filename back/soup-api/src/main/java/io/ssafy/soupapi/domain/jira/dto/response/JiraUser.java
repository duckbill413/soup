package io.ssafy.soupapi.domain.jira.dto.response;

import io.ssafy.soupapi.global.util.StringParserUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "지라 Simple 회원 정보")
public record JiraUser(
        @Schema(description = "지라 회원 Id")
        String id,
        @Schema(description = "지라 회원 이메일")
        String email,
        @Schema(description = "지라 회원 display name")
        String name
) {
    @Builder
    public JiraUser {
        id = StringParserUtil.parseNullToEmpty(id);
        email = StringParserUtil.parseNullToEmpty(email);
        name = StringParserUtil.parseNullToEmpty(name);
    }
}
