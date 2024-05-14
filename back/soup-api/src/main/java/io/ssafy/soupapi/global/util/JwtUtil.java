package io.ssafy.soupapi.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.ssafy.soupapi.global.security.dao.TokenRedisDao;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Log4j2
@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long accessTokenExpireTime;
    private final long refreshTokenExpireTime;
    private final String issuer;

    private final TokenRedisDao tokenRedisDao;

    private static final String JWT_TYPE = "JWT";
    private static final String ALGORITHM = "HS256";

    public JwtUtil(
        @Value(value = "${jwt.secret.key}")
        String jwtKey,
        @Value(value = "${jwt.expire-time.access-token}")
        long accessTokenExpireTime,
        @Value(value = "${jwt.expire-time.refresh-token}")
        long refreshTokenExpireTime,
        @Value(value = "${jwt.issuer}")
        String issuer,
        TokenRedisDao tokenRedisDao
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKey)); // Base64 형식으로 인코딩된 jwtKey를 디코딩 후 byte array 반환 -> 이에 적절한 HMAC 알고리즘을 적용해 Key(java.security.Key) 객체를 생성
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
        this.issuer = issuer;
        this.tokenRedisDao = tokenRedisDao;
    }

    public String generateToken(UserSecurityDTO userSecurityDTO, long expireTime) {
        Date expirationDate = new Date(new Date().getTime() + expireTime * 1000);

        return Jwts.builder()
                .header()
                    .type(JWT_TYPE)
                    .add("alg", ALGORITHM)
                    .add("generateDate", System.currentTimeMillis())
                .and() // return to JwtBuilder calls
                .signWith(secretKey, Jwts.SIG.HS256)
                .issuer(issuer)
                .expiration(expirationDate)
                .subject(userSecurityDTO.getUsername())
                .claim("roles", userSecurityDTO.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .compact();
    }

    public String generateAccessToken(UserSecurityDTO userSecurityDTO) {
        return generateToken(userSecurityDTO, accessTokenExpireTime);
    }

    public String generateRefreshToken(UserSecurityDTO userSecurityDTO) {
        String refreshToken = generateToken(userSecurityDTO, refreshTokenExpireTime);
        tokenRedisDao.save(userSecurityDTO.getId(), refreshToken, refreshTokenExpireTime);
        return refreshToken;
    }

    public Claims verifyJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean matchOrigin(UUID id, String refreshToken) {
        log.info("access token에 있던 uuid: {}", id);
        return tokenRedisDao.matchOrigin(id, refreshToken);
    }

}
