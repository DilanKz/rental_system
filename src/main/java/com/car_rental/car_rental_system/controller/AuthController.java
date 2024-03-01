package com.car_rental.car_rental_system.controller;

import com.car_rental.car_rental_system.dto.AuthenticationRequest;
import com.car_rental.car_rental_system.dto.AuthenticationResponse;
import com.car_rental.car_rental_system.dto.ResponseDTO;
import com.car_rental.car_rental_system.dto.UserDTO;
import com.car_rental.car_rental_system.service.JwtService;
import com.car_rental.car_rental_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dilan
 * @created 27/02/2024 - 01:55 pm
 */

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Endpoint for user login.
     *
     * @param request The AuthenticationRequest containing username and password
     * @return ResponseEntity containing the JWT token upon successful authentication
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> Login(@RequestBody AuthenticationRequest request){

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        // Generate JWT token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    /**
     * Endpoint for user registration.
     *
     * @param dto The UserDTO containing user registration information
     * @return ResponseEntity with a success message upon successful registration
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserDTO dto){
        userService.save(dto);
        return ResponseEntity.ok(new ResponseDTO(true,"User saved"));
    }

}
