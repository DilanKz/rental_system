package com.car_rental.car_rental_system.service;

import com.car_rental.car_rental_system.dto.VehicleDTO;
import com.car_rental.car_rental_system.entity.enums.VehicleModels;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 08:56 pm
 */
public interface VehicleService {

    /**
     * Retrieves all vehicles.
     *
     * @return List of VehicleDTO representing all vehicles
     */
    List<VehicleDTO> findAll();

    /**
     * Retrieves a vehicle by its ID.
     *
     * @param id The ID of the vehicle to retrieve
     * @return VehicleDTO representing the requested vehicle, or null if not found
     */
    VehicleDTO findById(int id);

    /**
     * Retrieves a list of vehicles available on the specified date.
     *
     * @param date The date for which available vehicles are to be retrieved
     * @return List of VehicleDTO representing the vehicles available on the specified date
     */
    List<VehicleDTO> findAllByDate(LocalDate date);

    /**
     * Saves a new vehicle.
     *
     * @param dto The VehicleDTO representing the vehicle to be saved
     */
    void save(VehicleDTO dto);

    /**
     * Updates an existing vehicle.
     *
     * @param dto The VehicleDTO representing the updated vehicle information
     */
    void update(VehicleDTO dto);

    /**
     * Retrieves a vehicle by its plate number.
     *
     * @param plateNumber The plate number of the vehicle to retrieve
     * @return VehicleDTO representing the requested vehicle, or null if not found
     */
    VehicleDTO findByPlateNumber(String plateNumber);

    /**
     * Retrieves a vehicle by its plate number.
     *
     * @param plateNumber The plate number of the vehicle to retrieve
     * @return List of VehicleDTO representing all vehicles
     */
    List<VehicleDTO> findAllByPlateNumber(String plateNumber);

    /**
     * Retrieves vehicles by model.
     *
     * @param model The model of the vehicles to retrieve
     * @return List<VehicleDTO> representing the requested vehicles with the specified model
     */
    List<VehicleDTO> findByModel(VehicleModels model);
}
