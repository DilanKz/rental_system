package com.car_rental.car_rental_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Dilan
 * @created 27/02/2024 - 01:58 pm
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AuthenticationRequest {
    private String username;
    private String password;
}
