package com.car_rental.car_rental_system.repo;

import com.car_rental.car_rental_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Dilan
 * @created 27/02/2024 - 10:34 am
 */
public interface UserRepository extends JpaRepository<User,Integer> {

    /**
     * Retrieves an optional user by the specified username.
     *
     * @param username The username of the user to retrieve
     * @return An optional containing the user with the specified username, or empty if not found
     */
    Optional<User> findByUsername (String username);
}
