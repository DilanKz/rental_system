package com.car_rental.car_rental_system.service;

import com.car_rental.car_rental_system.dto.RideRequestDTO;
import com.car_rental.car_rental_system.entity.embedded.LocationDetails;
import com.car_rental.car_rental_system.entity.enums.RequestStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 08:56 pm
 */
public interface RideRequestService {

    /**
     * Retrieves all ride requests.
     *
     * @return List of RideRequestDTO representing all ride requests
     */
    List<RideRequestDTO> findAll();

    /**
     * Retrieves ride requests by status.
     *
     * @param status The status of the ride requests to retrieve
     * @return List of RideRequestDTO representing ride requests with the specified status
     */
    List<RideRequestDTO> findByState(RequestStatus status);

    /**
     * Retrieves all ride requests associated with the specified user ID.
     *
     * @param id The ID of the user whose ride requests are to be retrieved
     * @return A list of RideRequestDTOs representing the ride requests associated with the user
     */
    List<RideRequestDTO> findAllRequestsByUserId(int id);

    /**
     * Retrieves a ride request by its ID.
     *
     * @param id The ID of the ride request to retrieve
     * @return RideRequestDTO representing the requested ride request, or null if not found
     */
    RideRequestDTO findById(int id);

    /**
     * Saves a new ride request.
     *
     * @param dto The RideRequestDTO representing the ride request to be saved
     */
    void save(RideRequestDTO dto);

    /**
     * Updates an existing ride request.
     *
     * @param dto The RideRequestDTO representing the updated ride request information
     */
    void update(RideRequestDTO dto);

    /**
     * Updates the status of an existing ride request.
     *
     * @param id     The ID of the ride request to update
     * @param status The new status to set for the ride request
     */
    void updateStatus(int id, RequestStatus status);

    /**
     * Assigns a vehicle to an existing ride request.
     *
     * @param id        The ID of the ride request to update
     * @param vehicleId The ID of the vehicle to assign to the ride request
     */
    void assignVehicle(int id, int vehicleId);

    /**
     * Finds ride requests based on the specified pickup location and destination.
     *
     * @param pickupLocation The details of the pickup location
     * @param destination    The details of the destination
     * @return List of RideRequestDTO representing ride requests matching the pickup location and destination
     */
    List<RideRequestDTO> findByPickupLocationAndDestination(LocationDetails pickupLocation, LocationDetails destination);

    /**
     * Retrieves ride requests filtered by the specified pickup date.
     *
     * @param date The pickup date to filter the ride requests
     * @return List of RideRequestDTO representing ride requests with the specified pickup date
     */
    List<RideRequestDTO> filterFromDate(LocalDate date);

    /**
     * Retrieves ride requests filtered by the specified pickup date range.
     *
     * @param startDate The start date of the pickup date range
     * @param endDate   The end date of the pickup date range
     * @return List of RideRequestDTO representing ride requests within the specified pickup date range
     */
    List<RideRequestDTO> filterBetweenDate(LocalDate startDate, LocalDate endDate);
}
