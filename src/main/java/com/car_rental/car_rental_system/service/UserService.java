package com.car_rental.car_rental_system.service;

import com.car_rental.car_rental_system.dto.UserDTO;

import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 12:26 pm
 */
public interface UserService {
    List<UserDTO> findAll();

    void save(UserDTO userDTO);

    void update(UserDTO userDTO);

    UserDTO findById(int id);

    UserDTO findByUsername(String username);

    void delete(int id);
}
