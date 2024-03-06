package com.car_rental.car_rental_system.service.impl;

import com.car_rental.car_rental_system.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dilan
 * @created 27/02/2024 - 08:31 am
 */

@Service
public class JwtServiceImpl implements JwtService {
    private static final Logger log = LoggerFactory.getLogger(JwtServiceImpl.class);
    private final String SECRET_KEY = "3A1F79A64C0B8E9CF29A670BBD8F5A436A3D344B2A683F0A3B2507E53BB1CD07";
    private static final int MINUTES = 60;

    private UserDetailsService userDetailsService;

    public JwtServiceImpl(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Generates a JWT token for the given username.
     *
     * @param username The username for which the token will be generated
     * @return The generated JWT token
     */
    @Override
    public String generateToken(String username) {
        log.info("Generating JWT token for username: {} in JwtServiceImpl", username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        try {
            Instant now = Instant.now();
            return Jwts.builder()
                    .setSubject(username)
                    .claim("role",userDetails.getAuthorities())
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(now.plus(MINUTES, ChronoUnit.MINUTES)))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();

        } catch (Exception e) {
            log.error("Error occurred while generating JWT token in JwtServiceImpl: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token The JWT token from which to extract the username
     * @return The username extracted from the token
     */
    @Override
    public String extractUsername(String token) {
        log.info("Extracting username from JWT token in JwtServiceImpl");
        try {
            return getTokenBody(token).getSubject();
        } catch (Exception e) {
            log.error("Error occurred while extracting username from JWT token in JwtServiceImpl: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Retrieves the roles (authorities) from the given JWT token.
     *
     * @param token The JWT token from which to retrieve the roles.
     * @return A collection of GrantedAuthority representing the roles extracted from the token.
     */
    @Override
    public Collection<? extends GrantedAuthority> getRolesFromToken(String token) {
        log.info("Extracting roles from JWT token in JwtServiceImpl");
        try {
            Claims claims = getTokenBody(token);
            List<Map<String, String>> roleClaims = (List<Map<String, String>>) claims.get("role");
            return roleClaims.stream()
                    .map(roleClaim -> new SimpleGrantedAuthority(roleClaim.get("authority")))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while extracting roles from JWT token in JwtServiceImpl: {}", e.getMessage());
            throw e;
        }
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
        log.info("Validating JWT token in JwtServiceImpl");
        try {
            final String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (Exception e) {
            log.error("Error occurred while validating JWT token in JwtServiceImpl: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Parses the JWT token and retrieves its claims.
     *
     * @param token The JWT token to parse
     * @return The claims extracted from the token
     * @throws AccessDeniedException If the token is expired
     */
    private Claims getTokenBody(String token) {
        log.info("Parsing JWT token and retrieving claims in JwtServiceImpl");
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("JWT token expired in JwtServiceImpl: {}", e.getMessage());
            throw new AccessDeniedException("Access denied: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error occurred while parsing JWT token and retrieving claims in JwtServiceImpl: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token The JWT token to check
     * @return True if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        log.info("Checking if JWT token is expired in JwtServiceImpl");
        try {
            Claims claims = getTokenBody(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("Error occurred while checking if JWT token is expired in JwtServiceImpl: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Retrieves the signing key used for JWT token validation.
     *
     * @return The signing key
     */
    private Key getSignInKey() {
        log.info("Retrieving signing key for JWT token validation in JwtServiceImpl");
        try {

            byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
            return Keys.hmacShaKeyFor(keyBytes);

        } catch (Exception e) {
            log.error("Error occurred while retrieving signing key for JWT token validation in JwtServiceImpl: {}", e.getMessage());
            throw e;
        }
    }

}
