package com.car_rental.car_rental_system.service;

import com.car_rental.car_rental_system.dto.VehicleDTO;

import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 08:56 pm
 */
public interface VehicleService {

    List<VehicleDTO> findAll();

    VehicleDTO findById(int id);

    void save(VehicleDTO dto);

    void update(VehicleDTO dto);

    VehicleDTO findByPlateNumber(String plateNumber);

    VehicleDTO findByModel(String model);
}
