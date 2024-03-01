package com.car_rental.car_rental_system.service.impl;

import com.car_rental.car_rental_system.dto.UserDTO;
import com.car_rental.car_rental_system.entity.Admin;
import com.car_rental.car_rental_system.entity.User;
import com.car_rental.car_rental_system.exceptions.BadCredentials;
import com.car_rental.car_rental_system.repo.AdminRepository;
import com.car_rental.car_rental_system.repo.UserRepository;
import com.car_rental.car_rental_system.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 12:45 pm
 */

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AdminRepository adminRepository;

    public UserServiceImpl(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    /**
     * Retrieves all users.
     *
     * @return List of UserDTO representing all users
     */
    @Override
    public List<UserDTO> findAll() {
        try {

            List<User> users = userRepository.findAll();
            List<UserDTO> userDTOS = new ArrayList<>();

            for (User user : users) {
                userDTOS.add(new UserDTO(user.getUid(), user.getName(), user.getEmail(), user.getUsername(), user.getPassword()));
            }

            return userDTOS;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Saves a new user.
     *
     * @param userDTO The UserDTO representing the user to be saved
     * @throws BadCredentials If the username is not available (already exists)
     */
    @Override
    public void save(UserDTO userDTO) {
        try {

            UserDTO dto = findByUsername(userDTO.getUsername());
            Admin admin = adminRepository.findByUsername(userDTO.getUsername()).orElse(null);
            System.out.println(admin);

            if (dto != null || admin != null) {
                throw new BadCredentials("Username is not available");
            }

            userRepository.save(new User(0, userDTO.getName(), userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword()));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * Updates an existing user.
     *
     * @param userDTO The UserDTO representing the updated user information
     * @throws BadCredentials If the user with the specified ID is not found
     */
    @Override
    public void update(UserDTO userDTO) {
        try {

            UserDTO dto = findById(userDTO.getUid());

            if (dto == null) {
                throw new BadCredentials("User not found");
            }

            userRepository.save(new User(userDTO.getUid(), userDTO.getName(), userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword()));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve
     * @return UserDTO representing the requested user, or null if not found
     */
    @Override
    public UserDTO findById(int id) {
        try {

            User user = userRepository.findById(id).orElse(null);

            if (user == null) return null;

            return new UserDTO(user.getUid(), user.getName(), user.getEmail(), user.getUsername(), user.getPassword());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve
     * @return UserDTO representing the requested user, or null if not found
     */
    @Override
    public UserDTO findByUsername(String username) {
        try {

            User user = userRepository.findByUsername(username).orElse(null);

            if (user == null) return null;

            return new UserDTO(user.getUid(), user.getName(), user.getEmail(), user.getUsername(), user.getPassword());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete
     */
    @Override
    public void delete(int id) {
        try {

            userRepository.deleteById(id);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
