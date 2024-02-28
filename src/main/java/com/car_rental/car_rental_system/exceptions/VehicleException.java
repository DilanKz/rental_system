package com.car_rental.car_rental_system.exceptions;

/**
 * @author Dilan
 * @created 28/02/2024 - 07:51 am
 */
public class VehicleException extends RuntimeException {

    public VehicleException() {
    }

    public VehicleException(String message) {
        super(message);
    }

    public VehicleException(String message, Throwable cause) {
        super(message, cause);
    }

    public VehicleException(Throwable cause) {
        super(cause);
    }

    public VehicleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
