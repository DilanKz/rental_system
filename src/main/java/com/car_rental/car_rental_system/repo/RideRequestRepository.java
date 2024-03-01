package com.car_rental.car_rental_system.repo;

import com.car_rental.car_rental_system.entity.RideRequest;
import com.car_rental.car_rental_system.entity.User;
import com.car_rental.car_rental_system.entity.embedded.LocationDetails;
import com.car_rental.car_rental_system.entity.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 10:36 am
 */
public interface RideRequestRepository extends JpaRepository<RideRequest,Integer> {

    /**
     * Retrieves a list of ride requests with the specified status.
     *
     * @param status The status of the ride requests to retrieve
     * @return A list of ride requests with the specified status
     */
    List<RideRequest> findAllByStatus(RequestStatus status);

    /**
     * Retrieves a list of ride requests with the specified pickup location and destination.
     *
     * @param pickupLocation The pickup location of the ride requests to retrieve
     * @param destination    The destination of the ride requests to retrieve
     * @return A list of ride requests with the specified pickup location and destination
     */
    List<RideRequest> findByPickupLocationAndDestination(LocationDetails pickupLocation, LocationDetails destination);

    /**
     * Retrieves a list of ride requests with the specified pickup date.
     *
     * @param date The pickup date of the ride requests to retrieve
     * @return A list of ride requests with the specified pickup date
     */
    List<RideRequest> findAllByPickupDate(LocalDate date);

    /**
     * Retrieves a list of ride requests with pickup dates between the specified start and end dates.
     *
     * @param startDate The start date of the pickup dates to retrieve
     * @param endDate   The end date of the pickup dates to retrieve
     * @return A list of ride requests with pickup dates between the specified start and end dates
     */
    List<RideRequest> findAllByPickupDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Retrieves all ride requests associated with the specified user ID.
     *
     * @param userId The ID of the user whose ride requests are to be retrieved
     * @return A list of RideRequest entities representing the ride requests associated with the user,
     *         or an empty list if no ride requests are found for the user ID
     */
    List<RideRequest> findAllByUser (User userId);
}
