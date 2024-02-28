package com.car_rental.car_rental_system.repo;

import com.car_rental.car_rental_system.entity.RideRequest;
import com.car_rental.car_rental_system.entity.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 10:36 am
 */
public interface RideRequestRepository extends JpaRepository<RideRequest,Integer> {
    List<RideRequest> findAllByStatus(RequestStatus status);
}
