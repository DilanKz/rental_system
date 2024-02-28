package com.car_rental.car_rental_system.service.impl;

import com.car_rental.car_rental_system.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author Dilan
 * @created 27/02/2024 - 08:31 am
 */

@Service
public class JwtServiceImpl implements JwtService {

    private final String SECRET_KEY = "3A1F79A64C0B8E9CF29A670BBD8F5A436A3D344B2A683F0A3B2507E53BB1CD07";
    private static final int MINUTES = 60;

    /**
     * Generates a JWT token for the given username.
     *
     * @param username The username for which the token will be generated
     * @return The generated JWT token
     */
    @Override
    public String generateToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(MINUTES, ChronoUnit.MINUTES)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token The JWT token from which to extract the username
     * @return The username extracted from the token
     */
    @Override
    public String extractUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
     * Validates the given JWT token against the provided UserDetails.
     *
     * @param token       The JWT token to validate
     * @param userDetails The UserDetails against which to validate the token
     * @return True if the token is valid for the provided UserDetails, false otherwise
     */
    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Parses the JWT token and retrieves its claims.
     *
     * @param token The JWT token to parse
     * @return The claims extracted from the token
     * @throws AccessDeniedException If the token is expired
     */
    private Claims getTokenBody(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AccessDeniedException("Access denied: " + e.getMessage());
        }
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token The JWT token to check
     * @return True if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        Claims claims = getTokenBody(token);
        return claims.getExpiration().before(new Date());
    }

    /**
     * Retrieves the signing key used for JWT token validation.
     *
     * @return The signing key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
