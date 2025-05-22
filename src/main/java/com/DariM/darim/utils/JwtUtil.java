

package com.DariM.darim.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    @Value("${jwt.allowed-clock-skew-ms:300000}")
    private long allowedClockSkewMs;

    private SecretKey key;

    

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // Modifier la méthode validateToken pour une meilleure gestion de l'expiration

public boolean validateToken(String token, UserDetails userDetails) {
    try {
        Claims claims = getClaimsFromToken(token);
        final String username = claims.getSubject();
        Date expiration = claims.getExpiration();
        Date now = new Date();
        
        // Ajouter une tolérance pour l'horloge (clock skew)
        Date adjustedNow = new Date(now.getTime() - allowedClockSkewMs);
        
        return username.equals(userDetails.getUsername()) 
               && expiration.after(adjustedNow);
    } catch (ExpiredJwtException ex) {
        System.out.println("Token expired but might be refreshable");
        return false;
    } catch (Exception ex) {
        System.out.println("Invalid token: " + ex.getMessage());
        return false;
    }
}
private boolean isTokenExpired(Claims claims) {
    return claims.getExpiration().before(new Date());
}

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String refreshToken(String token) throws ExpiredJwtException {
        Claims claims = getClaimsFromToken(token);
        
        // Vérifie si le token est trop vieux pour être rafraîchi (7 jours max)
        if (System.currentTimeMillis() - claims.getIssuedAt().getTime() > TimeUnit.DAYS.toMillis(7)) {
            throw new ExpiredJwtException(null, claims, "Token too old for refresh");
        }
        
        return generateToken(claims.getSubject());
    }
}