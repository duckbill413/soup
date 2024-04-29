package io.ssafy.soupapi.global.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.ssafy.soupapi.global.security.exception.AccessTokenException;
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
        log.info("[LSH] getAccessTokenFromRequestHeader() -> {}", header);
        if (Objects.isNull(header)) return null;

        // token type이 정의되지 않음
        String[] separatedToken = header.split(" ");
        if (separatedToken.length != 2) {
            throw new AccessTokenException(AccessTokenException.ACCESS_TOKEN_ERROR.TOO_SHORT);
        }

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
        }
    }

}
