package com.car_rental.car_rental_system.controller;

import com.car_rental.car_rental_system.dto.UserDTO;
import com.car_rental.car_rental_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dilan
 * @created 27/02/2024 - 04:08 pm
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    public ResponseEntity<String> updateAccount(@RequestBody UserDTO userDTO){

        userService.update(userDTO);
        return ResponseEntity.ok("User updated");
    }
}
