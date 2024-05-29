package com.example.heroesbackend.security.jwt;

import com.example.heroesbackend.security.entity.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Log4j2
public class JwtProvider {


    private static final String SECRET = "secret";
    private static final int EXPIRATION = 3600;

    private static final String COOKIE = "devsoft";

    private JwtProvider() {

    }


    public static String generateToken(Authentication authentication) {


        log.info("Secret: " + SECRET);
        log.info("Expiration: " + EXPIRATION);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Map<String, Object> extra = new HashMap<>();

        extra.put("id", userPrincipal.getId());
        extra.put("name", userPrincipal.getName());
        extra.put("email", userPrincipal.getEmail());

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRATION * 1000L))
                .addClaims(extra)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public static boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
