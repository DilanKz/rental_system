package com.car_rental.car_rental_system.repo;

import com.car_rental.car_rental_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dilan
 * @created 27/02/2024 - 10:34 am
 */
public interface UserRepository extends JpaRepository<User,Integer> {
}
