package com.car_rental.car_rental_system.dto;

import com.car_rental.car_rental_system.entity.User;
import com.car_rental.car_rental_system.entity.Vehicle;
import com.car_rental.car_rental_system.entity.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Dilan
 * @created 27/02/2024 - 05:44 pm
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RideRequestDTO {

    private int reqNo;

    private String name;

    private Date pickupDate;

    private Date returnDate;

    private String pickupLocation;

    private String destination;

    private RequestStatus status;

    private int vehicle;

    private int user;
}
