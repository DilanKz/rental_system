package com.car_rental.car_rental_system.repo;

import com.car_rental.car_rental_system.entity.Vehicle;
import com.car_rental.car_rental_system.entity.enums.VehicleModels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Dilan
 * @created 27/02/2024 - 10:36 am
 */
public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {

    /**
     * Retrieves an optional vehicle by the specified model.
     *
     * @param model The model of the vehicle to retrieve
     * @return An optional containing the vehicle with the specified model, or empty if not found
     */
    Optional<Vehicle> findByModel(VehicleModels model);

    /**
     * Retrieves an optional vehicle by the specified plate number.
     *
     * @param plateNumber The plate number of the vehicle to retrieve
     * @return An optional containing the vehicle with the specified plate number, or empty if not found
     */
    Optional<Vehicle> findByPlateNumber(String plateNumber);

    /**
     * Retrieves a list of vehicles whose plate numbers contain the specified value, ignoring case.
     *
     * @param plateNumber The partial or full plate number to search for
     * @return A list of vehicles whose plate numbers contain the specified value, ignoring case
     */
    List<Vehicle> findAllByPlateNumberContainingIgnoreCase(String plateNumber);

    /**
     * Retrieves a list of vehicles by the specified model.
     *
     * @param model The model of the vehicles to retrieve
     * @return A list of vehicles with the specified model
     */
    List<Vehicle> findAllByModel(VehicleModels model);

    /**
     * Retrieves a list of vehicles with request dates less than the specified date.
     *
     * @param date The date used to filter vehicles by request dates
     * @return A list of vehicles with request dates less than the specified date
     */
    List<Vehicle> findAllByReqDatesLessThan(LocalDate date);
}
