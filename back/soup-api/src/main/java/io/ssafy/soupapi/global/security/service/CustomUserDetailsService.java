package io.ssafy.soupapi.global.security.service;

import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.member.entity.MemberRole;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.ssafy.soupapi.global.util.FindEntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final FindEntityUtil findEntityUtil;

    @Transactional(readOnly = true)
    @Override
    public UserSecurityDTO loadUserByUsername(String id) {
        var member = findEntityUtil.findMemberById(UUID.fromString(id));

        return UserSecurityDTO.fromSocial()
                .username(member.getId().toString())
                .password(UUID.randomUUID().toString())
                .authorities(getMemberAuthorities(member))
                .create();
    }

    private static Collection<? extends GrantedAuthority> getMemberAuthorities(Member member) {
        return AuthorityUtils.createAuthorityList(
//                member.getRole().stream().map(Enum::name).toList()
                List.of("ROLE_" + MemberRole.USER)
        );
    }

}
