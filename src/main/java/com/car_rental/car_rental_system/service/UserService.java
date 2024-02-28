package com.car_rental.car_rental_system.service;

import com.car_rental.car_rental_system.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 12:26 pm
 */

public interface UserService {

    /**
     * Retrieves all users.
     *
     * @return List of UserDTO representing all users
     */
    List<UserDTO> findAll();

    /**
     * Saves a new user.
     *
     * @param userDTO The UserDTO representing the user to be saved
     */
    void save(UserDTO userDTO);

    /**
     * Updates an existing user.
     *
     * @param userDTO The UserDTO representing the updated user information
     */
    void update(UserDTO userDTO);

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve
     * @return UserDTO representing the requested user, or null if not found
     */
    UserDTO findById(int id);

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve
     * @return UserDTO representing the requested user, or null if not found
     */
    UserDTO findByUsername(String username);

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete
     */
    void delete(int id);
}
