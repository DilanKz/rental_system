package com.car_rental.car_rental_system.service.impl;

import com.car_rental.car_rental_system.dto.AdminDTO;
import com.car_rental.car_rental_system.entity.Admin;
import com.car_rental.car_rental_system.repo.AdminRepository;
import com.car_rental.car_rental_system.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * @author Dilan
 * @created 27/02/2024 - 12:44 pm
 */

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    @Override
    public AdminDTO findByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username).orElse(null);

        if (admin == null) return null;

        return new AdminDTO(admin.getId(), admin.getUsername(), admin.getPassword());
    }
}
