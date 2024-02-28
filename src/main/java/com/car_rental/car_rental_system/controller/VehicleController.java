package com.car_rental.car_rental_system.controller;

import com.car_rental.car_rental_system.dto.VehicleDTO;
import com.car_rental.car_rental_system.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> save(@RequestBody VehicleDTO dto){
        vehicleService.save(dto);

        return ResponseEntity.ok("Request is successfully sent");
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> update(@RequestBody VehicleDTO dto){
        vehicleService.update(dto);
        return ResponseEntity.ok("Request is successfully updated");
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity findAll(){
        List<VehicleDTO> list = vehicleService.findAll();

        if (list==null){
            return ResponseEntity.badRequest().body("Empty list");
        }

        return ResponseEntity.ok().body(list);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity getOne(@PathVariable int id){
        VehicleDTO vehicleDTO = vehicleService.findById(id);

        if (vehicleDTO==null){
            return ResponseEntity.badRequest().body("No vehicle found in this id");
        }

        return ResponseEntity.ok().body(vehicleDTO);

    }
}
