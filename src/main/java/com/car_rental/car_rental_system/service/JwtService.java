package com.car_rental.car_rental_system.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Collection;

/**
 * @author Dilan
 * @created 27/02/2024 - 12:20 pm
 */
public interface JwtService {

    /**
     * Generates a JWT token for the given username.
     *
     * @param username The username for which the token will be generated
     * @return The generated JWT token
     */
    String generateToken(String username);

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token The JWT token from which to extract the username
     * @return The username extracted from the token
     */
    String extractUsername(String token);

    /**
     * Retrieves the roles (authorities) from the given JWT token.
     *
     * @param token The JWT token from which to retrieve the roles.
     * @return A collection of GrantedAuthority representing the roles extracted from the token.
     */
    Collection<? extends GrantedAuthority> getRolesFromToken(String token);

    /**
     * Validates the given JWT token against the provided UserDetails.
     *
     * @param token       The JWT token to validate
     * @param userDetails The UserDetails against which to validate the token
     * @return True if the token is valid for the provided UserDetails, false otherwise
     */
    Boolean validateToken(String token, UserDetails userDetails);
}
