package com.car_rental.car_rental_system.service;

import com.car_rental.car_rental_system.dto.RideRequestDTO;

import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 08:56 pm
 */
public interface RideRequestService {

    List<RideRequestDTO> findAll();
    
    List<RideRequestDTO> findByState(String status);

    RideRequestDTO findById(int id);

    void save(RideRequestDTO dto);

    void update(RideRequestDTO dto);
}
