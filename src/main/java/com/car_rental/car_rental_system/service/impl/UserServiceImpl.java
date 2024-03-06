package com.car_rental.car_rental_system.service.impl;

import com.car_rental.car_rental_system.dto.UserDTO;
import com.car_rental.car_rental_system.entity.Admin;
import com.car_rental.car_rental_system.entity.User;
import com.car_rental.car_rental_system.exceptions.BadCredentials;
import com.car_rental.car_rental_system.repo.AdminRepository;
import com.car_rental.car_rental_system.repo.UserRepository;
import com.car_rental.car_rental_system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 12:45 pm
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
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
        log.info("Executing findAll method");
        try {

            List<User> users = userRepository.findAll();
            List<UserDTO> userDTOS = new ArrayList<>();

            for (User user : users) {
                userDTOS.add(new UserDTO(user.getUid(), user.getName(), user.getEmail(), user.getUsername(), user.getPassword(), user.getRole()));
            }

            log.info("Found {} users", userDTOS.size());

            return userDTOS;

        } catch (Exception e) {
            log.error("Error occurred while finding all users: {}", e.getMessage());
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
        log.info("Execute UserServiceImpl save: {}", userDTO.toString());
        try {
            UserDTO dto = findByUsername(userDTO.getUsername());
            Admin admin = adminRepository.findByUsername(userDTO.getUsername()).orElse(null);

            if (dto != null || admin != null) {
                throw new BadCredentials("Username is not available");
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userDTO.setPassword(encoder.encode(userDTO.getPassword()));

            userRepository.save(new User(0, userDTO.getName(), userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole()));

        } catch (Exception e) {
            log.error("Error in UserServiceImpl occurred while saving user: {}", e.getMessage());
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
        log.info("Executing UserServiceImpl update method with userDTO: {}", userDTO.toString());
        try {
            UserDTO dto = findById(userDTO.getUid());

            if (dto == null) {
                throw new BadCredentials("User not found");
            }

            userRepository.save(new User(userDTO.getUid(), userDTO.getName(), userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole()));
        } catch (Exception e) {
            log.error("Error in UserServiceImpl occurred while updating user: {}", e.getMessage());
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
        log.info("Executing UserServiceImpl findById method with id: {}", id);
        try {

            User user = userRepository.findById(id).orElse(null);

            if (user == null) return null;

            return new UserDTO(user.getUid(), user.getName(), user.getEmail(), user.getUsername(), user.getPassword(), user.getRole());

        } catch (Exception e) {
            log.error("Error in UserServiceImpl occurred while finding user by id {}: {}", id, e.getMessage());
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
        log.info("Executing UserServiceImpl findByUsername method with username: {}", username);
        try {

            User user = userRepository.findByUsername(username).orElse(null);

            if (user == null) return null;

            return new UserDTO(user.getUid(), user.getName(), user.getEmail(), user.getUsername(), user.getPassword(),user.getRole());

        } catch (Exception e) {
            log.error("Error in UserServiceImpl occurred in UserServiceImpl while finding user by username {}: {}", username, e.getMessage());
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
        log.info("Executing UserServiceImpl delete method with id: {}", id);
        try {

            UserDTO dto = findById(id);

            if (dto == null) {
                throw new BadCredentials("User not found");
            }

            userRepository.deleteById(id);
            log.info("User with id {} deleted successfully", id);

        } catch (Exception e) {
            log.error("Error in UserServiceImpl occurred while deleting user with id {}: {}", id, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
