package com.car_rental.car_rental_system.advisor;

import com.car_rental.car_rental_system.dto.ResponseDTO;
import com.car_rental.car_rental_system.exceptions.BadCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Dilan
 * @created 27/02/2024 - 09:24 am
 */

@RestControllerAdvice
@CrossOrigin
public class AppWideExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResponseDTO> handleServerException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(false,e.getMessage()));
    }

    @ExceptionHandler({BadCredentials.class})
    public ResponseEntity<ResponseDTO> handleAuthException(BadCredentials e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(false,e.getMessage()));
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ResponseDTO> handleCredentialsException(UsernameNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(false,e.getMessage()));
    }

}
