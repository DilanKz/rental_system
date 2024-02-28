package com.car_rental.car_rental_system.service.impl;

import com.car_rental.car_rental_system.dto.VehicleDTO;
import com.car_rental.car_rental_system.entity.Vehicle;
import com.car_rental.car_rental_system.entity.enums.VehicleModels;
import com.car_rental.car_rental_system.exceptions.VehicleException;
import com.car_rental.car_rental_system.repo.VehicleRepository;
import com.car_rental.car_rental_system.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 09:10 pm
 */

@Service
public class VehicleServiceImpl implements VehicleService {

    private VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Retrieves all vehicles.
     *
     * @return List of VehicleDTO representing all vehicles
     */
    @Override
    public List<VehicleDTO> findAll() {

        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<VehicleDTO> list = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            list.add(new VehicleDTO(vehicle.getVehicleId(), vehicle.getName(), vehicle.getModel(), vehicle.getPlateNumber(), vehicle.getReqDates()));
        }

        return list;
    }

    /**
     * Retrieves a vehicle by its ID.
     *
     * @param id The ID of the vehicle to retrieve
     * @return VehicleDTO representing the requested vehicle, or null if not found
     */
    @Override
    public VehicleDTO findById(int id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElse(null);

        if (vehicle == null) {
            return null;
        }

        return new VehicleDTO(vehicle.getVehicleId(), vehicle.getName(), vehicle.getModel(), vehicle.getPlateNumber(), vehicle.getReqDates());
    }

    /**
     * Saves a new vehicle.
     *
     * @param dto The VehicleDTO representing the vehicle to be saved
     * @throws VehicleException If a vehicle with the same plate number already exists
     */
    @Override
    public void save(VehicleDTO dto) {

        VehicleDTO vehicleDTO = findByPlateNumber(dto.getPlateNumber());

        //check if there is another vehicle exist by the same plate number if exist then throw an exception
        if (vehicleDTO != null) {
            throw new VehicleException("Vehicle in this plate number is already exists");
        }

        vehicleRepository.save(new Vehicle(dto.getVehicleId(), dto.getName(), dto.getModel(), dto.getPlateNumber(), dto.getReqDates()));
    }

    /**
     * Updates an existing vehicle.
     *
     * @param dto The VehicleDTO representing the updated vehicle information
     * @throws VehicleException If the vehicle to update is not found
     */
    @Override
    public void update(VehicleDTO dto) {

        VehicleDTO vehicleDTO = findByPlateNumber(dto.getPlateNumber());

        //check if there is another vehicle exist by the same plate number if exist then throw an exception
        if (vehicleDTO == null) {
            throw new VehicleException("No vehicle is found for update");
        }

        vehicleRepository.save(new Vehicle(dto.getVehicleId(), dto.getName(), dto.getModel(), dto.getPlateNumber(), dto.getReqDates()));
    }

    /**
     * Retrieves a vehicle by its plate number.
     *
     * @param plateNumber The plate number of the vehicle to retrieve
     * @return VehicleDTO representing the requested vehicle, or null if not found
     */
    @Override
    public VehicleDTO findByPlateNumber(String plateNumber) {
        Vehicle vehicle = vehicleRepository.findByPlateNumber(plateNumber).orElse(null);

        if (vehicle == null) {
            return null;
        }

        return new VehicleDTO(vehicle.getVehicleId(), vehicle.getName(), vehicle.getModel(), vehicle.getPlateNumber(), vehicle.getReqDates());
    }

    /**
     * Retrieves a vehicle by its model.
     *
     * @param model The model of the vehicle to retrieve
     * @return VehicleDTO representing the requested vehicle, or null if not found
     */
    @Override
    public VehicleDTO findByModel(VehicleModels model) {
        Vehicle vehicle = vehicleRepository.findByModel(model).orElse(null);

        if (vehicle == null) {
            return null;
        }

        return new VehicleDTO(vehicle.getVehicleId(), vehicle.getName(), vehicle.getModel(), vehicle.getPlateNumber(), vehicle.getReqDates());
    }
}
