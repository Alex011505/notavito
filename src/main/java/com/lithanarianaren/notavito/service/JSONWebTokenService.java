package com.lithanarianaren.notavito.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JSONWebTokenService {
    @Value("${security.jwt.secret}")
    private String secretKey;

    private static final long TOKEN_EXPIRATION = 24*60*60*1000;

    public String newToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public String getRole(String token) {
        return getClaim(token, claims -> claims.get("role", String.class));
    }

    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    public Boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, String username) {
        final String tokenUsername = getUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }
}
