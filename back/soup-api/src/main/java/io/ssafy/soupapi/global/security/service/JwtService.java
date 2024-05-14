package io.ssafy.soupapi.global.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.ssafy.soupapi.global.security.dto.TokenDto;
import io.ssafy.soupapi.global.security.exception.AccessTokenException;
import io.ssafy.soupapi.global.security.exception.RefreshTokenException;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.ssafy.soupapi.global.util.JwtClaimsParser;
import io.ssafy.soupapi.global.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String ACCESS_HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    // access token 생성
    public String createAccessToken(UserSecurityDTO userSecurityDTO) {
        return jwtUtil.generateAccessToken(userSecurityDTO);
    }

    // refresh token 생성
    public String createRefreshToken(UserSecurityDTO userSecurityDTO) {
        return jwtUtil.generateRefreshToken(userSecurityDTO);
    }

    public Authentication authenticateAccessToken(HttpServletRequest request) {
        String accessToken = getAccessTokenFromRequestHeader(request);

        UserSecurityDTO userSecurityDTO;
//        if (Objects.isNull(accessToken)) {
//            userSecurityDTO = UserSecurityDTO.fromSocial()
//                    .username(UUID.randomUUID().toString())
//                    .password(UUID.randomUUID().toString())
//                    .authorities(JwtClaimsParser.getAnonymousRole())
//                    .create();
//        } else {
            Claims claims = verifyJwtToken(accessToken);

            userSecurityDTO = UserSecurityDTO.fromSocial()
                    .username(claims.getSubject())
                    .password(UUID.randomUUID().toString())
                    .authorities(JwtClaimsParser.getMemberAuthorities(claims))
                    .create();
//        }

        return new UsernamePasswordAuthenticationToken(
                userSecurityDTO,
                userSecurityDTO.getPassword(),
                userSecurityDTO.getAuthorities()
        );
    }

    public String getAccessTokenFromRequestHeader(HttpServletRequest request) {
        String header = request.getHeader(ACCESS_HEADER_AUTHORIZATION);
        if (Objects.isNull(header)) return null;

        String[] separatedToken = header.split(" ");

//        if (separatedToken.length != 2) {
//            throw new AccessTokenException(AccessTokenException.ACCESS_TOKEN_ERROR.EMPTY);
//        }

        // 토큰이 Bearer 토큰 유형이 아님
        if (!separatedToken[0].equalsIgnoreCase(TOKEN_PREFIX)) {
            throw new AccessTokenException(AccessTokenException.ACCESS_TOKEN_ERROR.BAD_TYPE);
        }

        return separatedToken[1];
    }

    public Claims verifyJwtToken(String token) {
        try {
            return jwtUtil.verifyJwtToken(token);
        } catch (MalformedJwtException malformedJwtException) {
            throw new AccessTokenException(AccessTokenException.ACCESS_TOKEN_ERROR.MAL_FORMED);
        } catch (SignatureException signatureException) {
            throw new AccessTokenException(AccessTokenException.ACCESS_TOKEN_ERROR.BAD_SIGN);
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new AccessTokenException(AccessTokenException.ACCESS_TOKEN_ERROR.BAD_TYPE);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new AccessTokenException(AccessTokenException.ACCESS_TOKEN_ERROR.EXPIRED);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new AccessTokenException(AccessTokenException.ACCESS_TOKEN_ERROR.EMPTY);
        }
    }

    // refreshToken을 기반으로 accessToken과 refreshToken을 재발급
    public TokenDto regenerateJwtTokens(String refreshToken) throws RefreshTokenException{
        Claims claims = verifyJwtToken(refreshToken);
        String id = claims.getSubject();
        log.info("claim에 있는 id는: {}", id);

        // 서버에 저장된 refreshToken과 request로 주어진 refreshToken이 일치하는지 확인
        log.info("요청 받은 rt: {}", refreshToken);
        boolean matchOrigin = jwtUtil.matchOrigin(UUID.fromString(id), refreshToken);
        log.info("matchOrigin: {}", matchOrigin);
        if (!matchOrigin) {
            throw new RefreshTokenException(RefreshTokenException.REFRESH_TOKEN_ERROR.BAD_REFRESH);
        }

        UserSecurityDTO userSecurityDTO = userDetailsService.loadUserByUsername(id);
        String newAccessToken = createAccessToken(userSecurityDTO);
        String newRefreshToken = createRefreshToken(userSecurityDTO);
        log.info("새로 발급된 토큰은 at {} / rf {}", newAccessToken, newRefreshToken);

        return new TokenDto(newAccessToken, newRefreshToken);
    }

}