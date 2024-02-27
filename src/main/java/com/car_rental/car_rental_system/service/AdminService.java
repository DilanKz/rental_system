package com.car_rental.car_rental_system.service;

import com.car_rental.car_rental_system.dto.AdminDTO;
import org.springframework.stereotype.Service;

/**
 * @author Dilan
 * @created 27/02/2024 - 12:26 pm
 */
@Service
public interface AdminService {
    AdminDTO findByUsername(String username);
}
