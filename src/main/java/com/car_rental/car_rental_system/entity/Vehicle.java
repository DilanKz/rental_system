package com.car_rental.car_rental_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 10:17 am
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private int vehicleId;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "req_dates")
    private Date reqDates;

}
