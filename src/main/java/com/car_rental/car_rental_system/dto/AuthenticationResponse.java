package com.car_rental.car_rental_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Dilan
 * @created 27/02/2024 - 02:00 pm
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AuthenticationResponse {
    private String token;
}
