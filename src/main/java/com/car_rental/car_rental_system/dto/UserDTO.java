package com.car_rental.car_rental_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Dilan
 * @created 27/02/2024 - 12:27 pm
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

    private int uid;

    private String name;

    private String email;

    private String username;

    private String password;

    private String role;
}
