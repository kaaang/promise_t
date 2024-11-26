package kr.co.promise_t.core.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import kr.co.promise_t.core.user.UserId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final long accessTokenValidity = 1000 * 60 * 15; // 15분
    private final long refreshTokenValidity = 1000 * 60 * 60 * 24; // 1일

    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public UserJwt generateToken(UserId id) {
        var accessToken = this.createAccessToken(id);
        var refreshToken = this.createRefreshToken(id);

        return UserJwt.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn((int) (accessTokenValidity / 1000))
                .refreshExpiresIn((int) (refreshTokenValidity / 1000))
                .build();
    }

    private String createAccessToken(UserId id) {
        Claims claims = Jwts.claims().setSubject(id.getValue().toString());
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .setSubject(id.getValue().toString())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    private String createRefreshToken(UserId id) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .setSubject(id.getValue().toString())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
