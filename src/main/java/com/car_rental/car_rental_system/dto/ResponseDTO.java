package com.car_rental.car_rental_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dilan
 * @created 01/03/2024 - 04:26 pm
 */

@Data
@NoArgsConstructor
public class ResponseDTO {
    private boolean success;
    private String message;
    private Object body;

    public ResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseDTO(boolean success, Object body) {
        this.success = success;
        this.body = body;
    }

    public ResponseDTO(boolean success, String message, Object body) {
        this.success = success;
        this.message = message;
        this.body = body;
    }

}
