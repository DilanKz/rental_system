package com.car_rental.car_rental_system.repo;

import com.car_rental.car_rental_system.entity.Vehicle;
import com.car_rental.car_rental_system.entity.enums.VehicleModels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Dilan
 * @created 27/02/2024 - 10:36 am
 */
public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {
    Optional<Vehicle> findByModel(VehicleModels model);
    Optional<Vehicle> findByPlateNumber(String plateNumber);
    List<Vehicle> findAllByPlateNumberContainingIgnoreCase(String plateNumber);
    List<Vehicle> findAllByModel(VehicleModels model);
}
