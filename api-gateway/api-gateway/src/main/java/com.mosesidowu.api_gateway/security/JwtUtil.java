package com.mosesidowu.api_gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final String SECRET = "eyJzdWIiOiI2ODg2NDI1ZThlNGMwZjFhOGM1ODU5YjkiLCJlbWFpbCI6Im0xNG9zZTI2MTJAZXhhbXBsZS5jb20iLCJpYXQiOjE3NTM2ODU5ODUsImV4cCI6MTc1MzY4OTU4NX0.cM0QNGaVkgZBiu1s9S5siuvK3QhGcObLskZNjeAHVDQ";

    public Claims extractClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUserId(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }
}
