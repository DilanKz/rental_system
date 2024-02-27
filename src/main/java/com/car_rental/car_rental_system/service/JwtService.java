package com.car_rental.car_rental_system.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;

/**
 * @author Dilan
 * @created 27/02/2024 - 12:20 pm
 */
public interface JwtService {
    String generateToken(String username);
    String extractUsername(String token);
    Boolean validateToken(String token, UserDetails userDetails);
}
