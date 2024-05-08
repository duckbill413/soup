package io.ssafy.soupapi.global.external.liveblocks.dto.request;

import lombok.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public record UpdateRoomReq(
    List<String> defaultAccesses,
    HashMap<String, List<String>> usersAccesses,
    HashMap<String, String> metadata,
    HashMap<String, List<String>> groupsAccesses
) {

    @Builder
    public UpdateRoomReq {
        if (Objects.isNull(defaultAccesses)) {
            defaultAccesses = new ArrayList<>();
        }
        if (Objects.isNull(usersAccesses)) {
            usersAccesses = new HashMap<>();
        }
        if (Objects.isNull(metadata)) {
            metadata = new HashMap<>();
        }
        if (Objects.isNull(groupsAccesses)) {
            groupsAccesses = new HashMap<>();
        }
    }

}
