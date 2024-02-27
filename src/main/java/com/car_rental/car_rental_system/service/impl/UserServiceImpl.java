package com.car_rental.car_rental_system.service.impl;

import com.car_rental.car_rental_system.dto.UserDTO;
import com.car_rental.car_rental_system.entity.User;
import com.car_rental.car_rental_system.repo.UserRepository;
import com.car_rental.car_rental_system.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 12:45 pm
 */
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            userDTOS.add(new UserDTO(user.getUid(), user.getName(), user.getEmail(), user.getUsername(), user.getPassword()));
        }

        return userDTOS;
    }

    @Override
    public void save(UserDTO userDTO) {
        UserDTO dto = findByUsername(userDTO.getUsername());

        if (dto != null) {
            return;
        }

        userRepository.save(new User(0, userDTO.getName(), userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword()));

    }

    @Override
    public void update(UserDTO userDTO) {
        UserDTO dto = findByUsername(userDTO.getUsername());

        if (dto == null) {
            return;
        }

        userRepository.save(new User(userDTO.getUid(), userDTO.getName(), userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword()));
    }

    @Override
    public UserDTO findById(int id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) return null;

        return new UserDTO(user.getUid(), user.getName(), user.getEmail(), user.getUsername(), user.getPassword());
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) return null;

        return new UserDTO(user.getUid(), user.getName(), user.getEmail(), user.getUsername(), user.getPassword());
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
