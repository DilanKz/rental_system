package com.car_rental.car_rental_system.error;

/**
 * @author Dilan
 * @created 27/02/2024 - 04:14 pm
 */
public class BadCredentials extends RuntimeException {

    public BadCredentials() {
    }

    public BadCredentials(String message) {
        super(message);
    }

    public BadCredentials(String message, Throwable cause) {
        super(message, cause);
    }

    public BadCredentials(Throwable cause) {
        super(cause);
    }

    public BadCredentials(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

