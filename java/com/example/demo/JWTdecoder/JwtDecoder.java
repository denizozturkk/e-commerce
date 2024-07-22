package com.example.demo.JWTdecoder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtDecoder {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    public void decodeJwt(String jwtToken) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(jwtToken);

        Claims body = claimsJws.getBody();
        String subject = body.getSubject(); // This should be the login or username
        String role = body.get("role", String.class); // Extract custom claim 'role'

        System.out.println("Subject: " + subject);
        System.out.println("Role: " + role);
    }
}