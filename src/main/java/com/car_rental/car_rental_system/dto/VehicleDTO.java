package com.car_rental.car_rental_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Dilan
 * @created 27/02/2024 - 05:43 pm
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleDTO {

    private int vehicle;

    private String name;

    private String model;

    private String plateNumber;

    private String reqDates;
}
