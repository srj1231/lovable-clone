package com.saumya.projects.lovable_clone.security;

import com.saumya.projects.lovable_clone.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

@Component
public class AuthUtil {

    @Value("${jwt.secret}") // Injects the JWT secret key from application.yaml
    private String jwtSecret;

    private SecretKey getSecretKey() {
        // Converts the string secret into a SecretKey using HMAC-SHA algorithm
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        // Builds a JWT token with user details and expiration time
        // resulting token contains the user's identity information
        // cryptographically signed to prevent tampering
        return Jwts.builder()
                .subject(user.getName())
                .claim("userId", user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // valid for 10 minutes
                .signWith(getSecretKey())
                .compact();
    }

    /*
     * JWT token is used by clients in subsequent requests (typically in the Authorization header)
     * to prove their identity without re-sending credentials.
     */

    public JwtUserPrinciple verifyToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Long userId = Long.parseLong(claims.get("userId").toString());
        String userName = claims.getSubject();

        return new JwtUserPrinciple(
                userId, userName, new ArrayList<>()
        );
    }
}
