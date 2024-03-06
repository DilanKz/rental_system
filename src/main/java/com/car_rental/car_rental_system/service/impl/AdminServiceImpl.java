package com.car_rental.car_rental_system.service.impl;

import com.car_rental.car_rental_system.dto.AdminDTO;
import com.car_rental.car_rental_system.entity.Admin;
import com.car_rental.car_rental_system.repo.AdminRepository;
import com.car_rental.car_rental_system.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Dilan
 * @created 27/02/2024 - 12:44 pm
 */

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
    private AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Retrieves an administrator by their username.
     *
     * @param username The username of the administrator to retrieve
     * @return AdminDTO representing the requested administrator, or null if not found
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public AdminDTO findByUsername(String username) {
        log.info("Executing AdminServiceImpl findByUsername method with username: {}", username);
        try {

            // Retrieve admin entity from the repository by username
            Admin admin = adminRepository.findByUsername(username).orElse(null);

            // If admin not found, return null
            if (admin == null) {
                log.info("Admin with username {} not found in AdminServiceImpl", username);
                return null;
            }

            // Map admin entity to AdminDTO and return
            return new AdminDTO(admin.getId(), admin.getUsername(), admin.getPassword(), admin.getRole());

        }catch (Exception e){
            log.error("Error occurred in AdminServiceImpl while finding admin by username {}: {}", username, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
