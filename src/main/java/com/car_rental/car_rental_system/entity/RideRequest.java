package com.car_rental.car_rental_system.entity;

import com.car_rental.car_rental_system.entity.embedded.LocationDetails;
import com.car_rental.car_rental_system.entity.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

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

    @Column(name = "pickup_dates")
    private Date pickupDate;

    @Column(name = "return_dates")
    private Date returnDate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "pickup_address")),
            @AttributeOverride(name = "city", column = @Column(name = "pickup_city")),
            @AttributeOverride(name = "longitude", column = @Column(name = "pickup_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "pickup_latitude"))
    })
    private LocationDetails pickupLocation;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "destination_address")),
            @AttributeOverride(name = "city", column = @Column(name = "destination_city")),
            @AttributeOverride(name = "longitude", column = @Column(name = "destination_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "destination_latitude"))
    })
    private LocationDetails destination;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar(10) default 'PENDING'")
    private RequestStatus status;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id")
    private Vehicle car;

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User user;
}
