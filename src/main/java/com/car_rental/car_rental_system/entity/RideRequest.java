package com.car_rental.car_rental_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Dilan
 * @created 27/02/2024 - 10:21 am
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ride_request")
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "req_no")
    private int reqNo;

    @Column(name = "vehicle_model")
    private String name;

    @Column(name = "req_dates")
    private String date;

    @Column(name = "pickup_location")
    private String pickupLocation;

    @Column(name = "destination")
    private String destination;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id")
    private Vehicle car;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User user;
}
