package io.ssafy.soupapi.global.security;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

@Getter
public class TemporalMember extends User {
    private final UUID id;
    private final String email;

    @Builder(builderMethodName = "temporal", buildMethodName = "create")
    public TemporalMember(UUID id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.id = id;
        this.email = email;
    }
}
