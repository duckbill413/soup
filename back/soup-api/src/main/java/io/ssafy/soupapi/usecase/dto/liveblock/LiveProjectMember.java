package io.ssafy.soupapi.usecase.dto.liveblock;

import io.ssafy.soupapi.global.external.liveblocks.dto.outline.Role;

import java.util.List;

public record LiveProjectMember(
        String id,
        String name,
        String email,
        List<Role> roles
) {
}
