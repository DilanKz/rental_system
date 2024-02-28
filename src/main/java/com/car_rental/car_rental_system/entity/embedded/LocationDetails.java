package com.car_rental.car_rental_system.entity.embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Dilan
 * @created 28/02/2024 - 09:35 pm
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocationDetails {
    private String address;
    private String city;
    private double longitude;
    private double latitude;
}

