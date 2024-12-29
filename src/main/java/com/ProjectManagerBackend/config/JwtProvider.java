package com.ProjectManagerBackend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {

    @Value("${jwt.secret}")
    static private String jwtSecret;
    static SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());


    public static String generateJwtToken(Authentication auth) {

        String token = Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email", auth.getName())
                .signWith(key)
                .compact();

        return token;
    }

    public static String getTokenEmail(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build().parseClaimsJws(token)
                .getBody();

        String email = String.valueOf(claims.get("email"));

        return email;
    }

}
