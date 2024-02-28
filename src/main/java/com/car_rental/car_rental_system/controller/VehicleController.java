package com.car_rental.car_rental_system.controller;

import com.car_rental.car_rental_system.dto.VehicleDTO;
import com.car_rental.car_rental_system.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dilan
 * @created 28/02/2024 - 07:44 am
 */

@RestController
@CrossOrigin
@RequestMapping("/vehicle")
public class VehicleController {

    private VehicleService vehicleService;

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
    public ResponseEntity<String> save(@RequestBody VehicleDTO dto) {
        vehicleService.save(dto);

        return ResponseEntity.ok("Vehicle is successfully saved");
    }

    /**
     * Endpoint for updating an existing vehicle.
     *
     * @param dto The VehicleDTO containing updated information about the vehicle
     * @return ResponseEntity with a success message upon successful update
     */
    @PutMapping
    public ResponseEntity<String> update(@RequestBody VehicleDTO dto) {
        vehicleService.update(dto);
        return ResponseEntity.ok("Vehicle is successfully updated");
    }

    /**
     * Endpoint for retrieving all vehicles.
     *
     * @return ResponseEntity with a list of VehicleDTO representing all vehicles
     */
    @GetMapping
    public ResponseEntity findAll() {
        List<VehicleDTO> list = vehicleService.findAll();

        if (list == null) {
            return ResponseEntity.badRequest().body("Empty list");
        }

        return ResponseEntity.ok().body(list);

    }

    /**
     * Endpoint for retrieving a specific vehicle by its ID.
     *
     * @param id The ID of the vehicle to retrieve
     * @return ResponseEntity with the requested VehicleDTO if found, or a bad request message if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable int id) {
        VehicleDTO vehicleDTO = vehicleService.findById(id);

        if (vehicleDTO == null) {
            return ResponseEntity.badRequest().body("No vehicle found in this id");
        }

        return ResponseEntity.ok().body(vehicleDTO);

    }
}
