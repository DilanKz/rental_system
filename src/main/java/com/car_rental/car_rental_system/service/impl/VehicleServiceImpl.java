package com.car_rental.car_rental_system.service.impl;

import com.car_rental.car_rental_system.dto.VehicleDTO;
import com.car_rental.car_rental_system.entity.Vehicle;
import com.car_rental.car_rental_system.entity.enums.VehicleModels;
import com.car_rental.car_rental_system.exceptions.VehicleException;
import com.car_rental.car_rental_system.repo.VehicleRepository;
import com.car_rental.car_rental_system.service.VehicleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public List<VehicleDTO> findAll() {
        try {

            List<Vehicle> vehicles = vehicleRepository.findAll();
            List<VehicleDTO> list = new ArrayList<>();

            for (Vehicle vehicle : vehicles) {
                list.add(new VehicleDTO(vehicle.getVehicleId(), vehicle.getName(), vehicle.getModel(), vehicle.getPlateNumber(), vehicle.getReqDates()));
            }

            return list;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves a vehicle by its ID.
     *
     * @param id The ID of the vehicle to retrieve
     * @return VehicleDTO representing the requested vehicle, or null if not found
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public VehicleDTO findById(int id) {
        try {

            Vehicle vehicle = vehicleRepository.findById(id).orElse(null);

            if (vehicle == null) {
                return null;
            }

            return new VehicleDTO(vehicle.getVehicleId(), vehicle.getName(), vehicle.getModel(), vehicle.getPlateNumber(), vehicle.getReqDates());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of vehicles available on or before the specified date.
     *
     * @param date The date for which available vehicles are to be retrieved
     * @return List of VehicleDTO representing the vehicles available on or before the specified date
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public List<VehicleDTO> findAllByDate(LocalDate date) {
        try {

            List<Vehicle> vehicles = vehicleRepository.findAllByReqDatesLessThan(date);
            List<VehicleDTO> list = new ArrayList<>();

            for (Vehicle vehicle : vehicles) {
                list.add(new VehicleDTO(vehicle.getVehicleId(), vehicle.getName(), vehicle.getModel(), vehicle.getPlateNumber(), vehicle.getReqDates()));
            }

            return list;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Saves a new vehicle.
     *
     * @param dto The VehicleDTO representing the vehicle to be saved
     * @throws VehicleException If a vehicle with the same plate number already exists
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public void save(VehicleDTO dto) {
        try {

            VehicleDTO vehicleDTO = findByPlateNumber(dto.getPlateNumber());

            //check if there is another vehicle exist by the same plate number if exist then throw an exception
            if (vehicleDTO != null) {
                throw new VehicleException("Vehicle in this plate number is already exists");
            }

            vehicleRepository.save(new Vehicle(dto.getVehicleId(), dto.getName(), dto.getModel(), dto.getPlateNumber(), dto.getReqDates()));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Updates an existing vehicle.
     *
     * @param dto The VehicleDTO representing the updated vehicle information
     * @throws VehicleException If the vehicle to update is not found
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public void update(VehicleDTO dto) {
        try {

            VehicleDTO vehicleDTO = findByPlateNumber(dto.getPlateNumber());

            //check if there is another vehicle exist by the same plate number if exist then throw an exception
            if (vehicleDTO == null) {
                throw new VehicleException("No vehicle is found for update");
            }

            vehicleRepository.save(new Vehicle(dto.getVehicleId(), dto.getName(), dto.getModel(), dto.getPlateNumber(), dto.getReqDates()));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves a vehicle by its plate number.
     *
     * @param plateNumber The plate number of the vehicle to retrieve
     * @return VehicleDTO representing the requested vehicle, or null if not found
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public VehicleDTO findByPlateNumber(String plateNumber) {
        try {

            Vehicle vehicle = vehicleRepository.findByPlateNumber(plateNumber).orElse(null);

            if (vehicle == null) {
                return null;
            }

            return new VehicleDTO(vehicle.getVehicleId(), vehicle.getName(), vehicle.getModel(), vehicle.getPlateNumber(), vehicle.getReqDates());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of vehicles based on a partial or complete plate number match.
     *
     * @param plateNumber The partial or complete plate number to search for
     * @return List of VehicleDTO representing the vehicles matching the provided plate number
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public List<VehicleDTO> findAllByPlateNumber(String plateNumber) {
        try {

            List<Vehicle> vehicles = vehicleRepository.findAllByPlateNumberContainingIgnoreCase(plateNumber);
            List<VehicleDTO> list = new ArrayList<>();

            for (Vehicle vehicle : vehicles) {
                list.add(new VehicleDTO(vehicle.getVehicleId(), vehicle.getName(), vehicle.getModel(), vehicle.getPlateNumber(), vehicle.getReqDates()));
            }

            return list;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves a vehicle by its model.
     *
     * @param model The model of the vehicle to retrieve
     * @return List<VehicleDTO> representing the requested vehicle, or null if not found
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public List<VehicleDTO> findByModel(VehicleModels model) {
        try {

            List<Vehicle> vehicles = vehicleRepository.findAllByModel(model);
            List<VehicleDTO> list = new ArrayList<>();

            for (Vehicle vehicle : vehicles) {
                list.add(new VehicleDTO(vehicle.getVehicleId(), vehicle.getName(), vehicle.getModel(), vehicle.getPlateNumber(), vehicle.getReqDates()));
            }

            return list;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
