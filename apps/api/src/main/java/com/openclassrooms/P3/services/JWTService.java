package com.openclassrooms.P3.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    private final JwtEncoder jwtEncoder;

    public JWTService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(1, ChronoUnit.DAYS))
            .subject(authentication.getName())
            .claim("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
            .build();

        JwtEncoderParameters params = JwtEncoderParameters.from(
            JwsHeader.with(MacAlgorithm.HS256).build(), claims);

        return jwtEncoder.encode(params).getTokenValue();
    }
}
