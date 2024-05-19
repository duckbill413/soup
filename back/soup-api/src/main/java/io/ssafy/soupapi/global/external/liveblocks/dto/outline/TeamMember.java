package io.ssafy.soupapi.global.external.liveblocks.dto.outline;

import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class TeamMember {

    String id;
    String name;
    String email;
    List<Role> roles;

}
