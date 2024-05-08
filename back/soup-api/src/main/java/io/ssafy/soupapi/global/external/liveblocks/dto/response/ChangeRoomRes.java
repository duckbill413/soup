package io.ssafy.soupapi.global.external.liveblocks.dto.response;

import lombok.Builder;

import java.util.HashMap;
import java.util.List;

@Builder
public record ChangeRoomRes(
    String type,
    String id,
    String lastConnectionAt,
    String createdAt,
    HashMap<String, String> metadata,
    List<String> defaultAccesses,
    HashMap<String, List<String>> groupsAccesses,
    HashMap<String, List<String>> usersAccesses
) {
}
