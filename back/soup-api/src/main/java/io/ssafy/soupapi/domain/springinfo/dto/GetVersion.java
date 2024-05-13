package io.ssafy.soupapi.domain.springinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "spring version 정보")
public record GetVersion(
        String version
) {
}
