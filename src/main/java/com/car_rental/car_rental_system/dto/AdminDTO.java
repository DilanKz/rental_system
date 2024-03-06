package com.car_rental.car_rental_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Dilan
 * @created 27/02/2024 - 12:33 pm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminDTO {

    private int id;

    private String username;

    private String password;

    private String role;
}
