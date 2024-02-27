package com.car_rental.car_rental_system.repo;

import com.car_rental.car_rental_system.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dilan
 * @created 27/02/2024 - 10:36 am
 */
public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {
}
