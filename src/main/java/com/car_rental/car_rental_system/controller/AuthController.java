package com.car_rental.car_rental_system.controller;

import com.car_rental.car_rental_system.dto.AuthenticationRequest;
import com.car_rental.car_rental_system.dto.AuthenticationResponse;
import com.car_rental.car_rental_system.dto.UserDTO;
import com.car_rental.car_rental_system.service.JwtService;
import com.car_rental.car_rental_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dilan
 * @created 27/02/2024 - 01:55 pm
 */

@RestController
@CrossOrigin
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

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

    public ResponseEntity<String> register(@RequestBody UserDTO dto){
        userService.save(dto);
        return ResponseEntity.ok("User Registered");
    }

}
