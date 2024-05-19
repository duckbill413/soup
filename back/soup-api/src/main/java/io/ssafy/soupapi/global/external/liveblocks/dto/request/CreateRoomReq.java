package io.ssafy.soupapi.global.external.liveblocks.dto.request;

import lombok.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public record CreateRoomReq(
    String id, // required
    List<String> defaultAccesses, // required
    HashMap<String, String> metadata,
    HashMap<String, List<String>> usersAccesses, // needed for SOUP
    HashMap<String, List<String>> groupsAccesses
) {

    @Builder
    public CreateRoomReq {
        if (Objects.isNull(defaultAccesses)) {
            defaultAccesses = new ArrayList<>();
        }
        if (Objects.isNull(metadata)) {
            metadata = new HashMap<>();
        }
        if (Objects.isNull(usersAccesses)) {
            usersAccesses = new HashMap<>();
        }
        if (Objects.isNull(groupsAccesses)) {
            groupsAccesses = new HashMap<>();
        }
    }

}
