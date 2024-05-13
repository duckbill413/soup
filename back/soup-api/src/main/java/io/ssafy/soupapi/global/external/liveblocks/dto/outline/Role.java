package io.ssafy.soupapi.global.external.liveblocks.dto.outline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Role {
    private String id;
    @JsonProperty("role_name")
    private String name;
}
