package com.example.demo.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.DTOs.AdminDTO;
import com.example.demo.DTOs.ClientDTO;
import com.example.demo.enums.Roles;
import com.example.demo.users.admin.AdminService;
import com.example.demo.users.client.ClientService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

    private byte[] secretKeyBytes; // Store the key bytes directly

    private final AdminService adminService;
    private final ClientService clientService;

    private final Logger logger = Logger.getLogger(UserAuthenticationProvider.class.getName());

    @PostConstruct
    protected void init() {
        // Generate a secure secret key
        secretKeyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
    }

    public String createToken(String login, Roles role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        Algorithm algorithm = Algorithm.HMAC256(secretKeyBytes);
        String token = JWT.create()
                .withSubject(login)
                .withClaim("role", "ROLE_" + role.name())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
        logger.info("Generated JWT Token: " + token);
        return token;
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKeyBytes);

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);

        String login = decoded.getSubject();
        String role = decoded.getClaim("role").asString();

        if ("ROLE_ADMIN".equals(role)) {
            AdminDTO admin = adminService.findByLogin(login);
            return new UsernamePasswordAuthenticationToken(admin, null, Collections.singletonList(new SimpleGrantedAuthority(role)));
        } else if ("ROLE_CLIENT".equals(role)) {
            ClientDTO client = clientService.findByLogin(login);
            return new UsernamePasswordAuthenticationToken(client, null, Collections.singletonList(new SimpleGrantedAuthority(role)));
        }

        throw new JWTVerificationException("Role not recognized");
    }
}
