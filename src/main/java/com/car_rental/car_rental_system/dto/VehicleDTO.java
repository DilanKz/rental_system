package com.car_rental.car_rental_system.dto;

import com.car_rental.car_rental_system.entity.enums.VehicleModels;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

/**
 * @author Dilan
 * @created 27/02/2024 - 05:43 pm
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleDTO {

    private int vehicleId;

    private String name;

    private VehicleModels model;

    private String plateNumber;

    private Date reqDates;
}
