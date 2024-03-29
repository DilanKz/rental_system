package com.car_rental.car_rental_system.controller;

import com.car_rental.car_rental_system.dto.ResponseDTO;
import com.car_rental.car_rental_system.dto.UserDTO;
import com.car_rental.car_rental_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dilan
 * @created 27/02/2024 - 04:08 pm
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for updating user account information.
     *
     * @param userDTO The UserDTO containing updated user information
     * @return ResponseEntity with a success message upon successful update
     */
    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDTO> updateAccount(@RequestBody UserDTO userDTO){

        userService.update(userDTO);
        return ResponseEntity.ok(new ResponseDTO(true,"User updated"));
    }
}
