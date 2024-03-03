package com.car_rental.car_rental_system.controller;

import com.car_rental.car_rental_system.dto.ResponseDTO;
import com.car_rental.car_rental_system.dto.VehicleDTO;
import com.car_rental.car_rental_system.entity.enums.VehicleModels;
import com.car_rental.car_rental_system.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Dilan
 * @created 28/02/2024 - 07:44 am
 */

@RestController
@CrossOrigin
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Endpoint for saving a new vehicle.
     *
     * @param dto The VehicleDTO containing information about the vehicle to be saved
     * @return ResponseEntity with a success message upon successful save
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> save(@RequestBody VehicleDTO dto) {
        vehicleService.save(dto);

        return ResponseEntity.ok(new ResponseDTO(true,"Vehicle is successfully saved"));
    }

    /**
     * Endpoint for updating an existing vehicle.
     *
     * @param dto The VehicleDTO containing updated information about the vehicle
     * @return ResponseEntity with a success message upon successful update
     */
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> update(@RequestBody VehicleDTO dto) {
        vehicleService.update(dto);
        return ResponseEntity.ok(new ResponseDTO(true,"Vehicle is successfully updated"));
    }

    /**
     * Endpoint for retrieving all vehicles.
     *
     * @return ResponseEntity with a list of VehicleDTO representing all vehicles
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> findAll() {
        return ResponseEntity.ok(new ResponseDTO(true, "Retrieving all vehicles", vehicleService.findAll()));
    }

    /**
     * Endpoint for retrieving a specific vehicle by its ID.
     *
     * @param id The ID of the vehicle to retrieve
     * @return ResponseEntity with the requested VehicleDTO if found, or a bad request message if not found
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getOne(@PathVariable int id) {
        VehicleDTO vehicleDTO = vehicleService.findById(id);

        if (vehicleDTO == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO(false,"Bad Request"));
        }

        return ResponseEntity.ok(new ResponseDTO(true, "Retrieving vehicle by ID", vehicleDTO));

    }

    /**
     * Endpoint for retrieving all vehicles that match the specified plate number.
     *
     * @param plateNumber The plate number of the vehicles to retrieve
     * @return ResponseEntity with a list of VehicleDTO representing vehicles matching the plate number,
     *         or a bad request message if no vehicles are found
     */
    @GetMapping("/platenumber")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> findAllByPlateNumber(String plateNumber) {
        List<VehicleDTO> vehicleDTOS = vehicleService.findAllByPlateNumber(plateNumber);

        if (vehicleDTOS == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO(false,"Bad Request"));
        }

        return ResponseEntity.ok(new ResponseDTO(true, "Retrieving vehicles by plate number", vehicleDTOS));

    }

    /**
     * Endpoint for retrieving all vehicles that match the specified plate number.
     *
     * @param models The models of the vehicles to retrieve
     * @return ResponseEntity with a list of VehicleDTO representing vehicles matching the models,
     *         or a bad request message if no vehicles are found
     */
    @GetMapping("/model")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> findAllByModel(VehicleModels models) {
        List<VehicleDTO> vehicleDTOS = vehicleService.findByModel(models);

        if (vehicleDTOS == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO(false,"Bad Request"));
        }

        return ResponseEntity.ok(new ResponseDTO(true, "Retrieving vehicles by model", vehicleDTOS));

    }

    /**
     * Retrieves a list of vehicles that are available on or before the specified date.
     *
     * @param date The date for which available vehicles are to be retrieved
     * @return ResponseEntity containing a list of VehicleDTO representing the available vehicles
     */
    @GetMapping("/date")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> findAllByDate(LocalDate date) {
        List<VehicleDTO> vehicleDTOS = vehicleService.findAllByDate(date);

        return ResponseEntity.ok(new ResponseDTO(true, "Retrieving available vehicles by date", vehicleDTOS));

    }
}
