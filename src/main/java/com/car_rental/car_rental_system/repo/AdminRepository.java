package com.car_rental.car_rental_system.repo;

import com.car_rental.car_rental_system.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dilan
 * @created 27/02/2024 - 10:33 am
 */
public interface AdminRepository extends JpaRepository<Admin,Integer> {
}
