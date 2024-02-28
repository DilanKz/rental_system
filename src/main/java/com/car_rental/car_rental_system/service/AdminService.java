package com.car_rental.car_rental_system.service;

import com.car_rental.car_rental_system.dto.AdminDTO;
import org.springframework.stereotype.Service;

/**
 * @author Dilan
 * @created 27/02/2024 - 12:26 pm
 */
public interface AdminService {

    /**
     * Retrieves an administrator by their username.
     *
     * @param username The username of the administrator to retrieve
     * @return AdminDTO representing the requested administrator, or null if not found
     */
    AdminDTO findByUsername(String username);
}
