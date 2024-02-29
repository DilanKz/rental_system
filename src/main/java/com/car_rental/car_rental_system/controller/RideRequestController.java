package com.car_rental.car_rental_system.controller;

import com.car_rental.car_rental_system.dto.RideRequestDTO;
import com.car_rental.car_rental_system.dto.VehicleDTO;
import com.car_rental.car_rental_system.entity.embedded.LocationDetails;
import com.car_rental.car_rental_system.entity.enums.RequestStatus;
import com.car_rental.car_rental_system.service.RideRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Dilan
 * @created 28/02/2024 - 10:47 am
 */

@RestController
@CrossOrigin
@RequestMapping("/request")
public class RideRequestController {

    private final RideRequestService requestService;

    public RideRequestController(RideRequestService requestService) {
        this.requestService = requestService;
    }


    /**
     * Retrieves all ride requests.
     *
     * @return ResponseEntity with status OK and a list of all ride requests
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllRequests() {

        return ResponseEntity.ok(requestService.findAll());
    }

    /**
     * Retrieves a single ride request by its ID.
     *
     * @param id The ID of the ride request to retrieve
     * @return ResponseEntity with status OK and the requested ride request, or NOT_FOUND if the ID is not found
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Object> getOneRequest(@PathVariable int id) {

        RideRequestDTO requestDTO = requestService.findById(id);

        if (requestDTO == null) {
            return ResponseEntity.badRequest().body("No Request found for the given id");
        }

        return ResponseEntity.ok(requestDTO);

    }

    /**
     * Retrieves all ride requests associated with the specified user ID.
     *
     * @param id The ID of the user whose ride requests are to be retrieved
     * @return ResponseEntity containing a list of RideRequestDTOs with status code 200 (OK) if requests are found,
     *         or status code 404 (Not Found) if no requests are found
     */
    @GetMapping("/all/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Object> allRequests(@PathVariable int id) {
        List<RideRequestDTO> requestDTOs = requestService.findAllRequestsByUserId(id);

        if (requestDTOs.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no requests are found
        }

        return ResponseEntity.ok().body(requestDTOs); // Return requests with status code 200 (OK)
    }



    /**
     * Retrieves ride requests based on their status.
     *
     * @param status The status to filter ride requests
     * @return ResponseEntity with status OK and a list of ride requests with the specified status
     */
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getRequestOnStatus(@PathVariable RequestStatus status) {

        List<RideRequestDTO> byState = requestService.findByState(status);

        return ResponseEntity.ok(byState);
    }

    /**
     * Retrieves ride requests based on the specified pickup and destination locations.
     *
     * @param locationDetailsList A list containing pickup location details at index 0 and destination location details at index 1
     * @return ResponseEntity with a list of RideRequestDTO representing ride requests matching the pickup and destination locations
     */
    @GetMapping("/locations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getRequestByLocation(@RequestBody List<LocationDetails> locationDetailsList) {

        if (locationDetailsList.size() < 2) {
            return ResponseEntity.badRequest().build(); // Return bad request if the array size is less than 2
        }

        LocationDetails pickupLocation = locationDetailsList.get(0);
        LocationDetails destination = locationDetailsList.get(1);


        List<RideRequestDTO> requestDTOS = requestService.findByPickupLocationAndDestination(pickupLocation, destination);

        return ResponseEntity.ok(requestDTOS);
    }

    /**
     * Retrieves ride requests filtered by the specified pickup date.
     *
     * @param date The pickup date to filter the ride requests
     * @return ResponseEntity with a list of RideRequestDTO representing ride requests with the specified pickup date
     */
    @GetMapping("/date")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getRequestByDate(@RequestParam LocalDate date) {
        return ResponseEntity.ok(requestService.filterFromDate(date));
    }

    /**
     * Retrieves ride requests filtered by the specified pickup date range.
     *
     * @param startDate The start date of the pickup date range
     * @param endDate   The end date of the pickup date range
     * @return ResponseEntity with a list of RideRequestDTO representing ride requests within the specified pickup date range
     */
    @GetMapping("/dates")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getRequestByDates(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {

        return ResponseEntity.ok(requestService.filterBetweenDate(startDate, endDate));
    }

    /**
     * Adds a new ride request.
     *
     * @param dto The DTO containing the details of the new ride request
     * @return ResponseEntity with status OK and the newly created ride request, or BAD_REQUEST if the request is invalid
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> addRequest(@RequestBody RideRequestDTO dto) {

        requestService.save(dto);
        return ResponseEntity.ok("Request sent successfully");
    }


    /**
     * Updates an existing ride request.
     *
     * @param dto The DTO containing the updated details of the ride request
     * @return ResponseEntity with status OK and the updated ride request, or BAD_REQUEST if the request is invalid
     */
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Object> updateRequest(@RequestBody RideRequestDTO dto) {

        requestService.update(dto);
        return ResponseEntity.ok("Request updated successfully");
    }


    /**
     * Updates the status of an existing ride request.
     *
     * @param id     The ID of the ride request to update
     * @param status The new status to set for the ride request
     * @return ResponseEntity with status OK and a success message if the update is successful, or BAD_REQUEST if the request is invalid
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateRequestStatus(@PathVariable int id, @RequestParam RequestStatus status) {
        //Update request status to approve or reject
        requestService.updateStatus(id, status);

        return ResponseEntity.ok("Request status has been updated successfully.");
    }

    /**
     * Endpoint for assigning a vehicle to an existing ride request.
     *
     * @param id        The ID of the ride request to update
     * @param vehicleId The ID of the vehicle to assign to the ride request
     * @return ResponseEntity with a success message upon successful assignment
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> assignVehicle(@PathVariable int id, @RequestParam int vehicleId) {
        //assign vehicle to a request
        requestService.assignVehicle(id, vehicleId);

        return ResponseEntity.ok("Vehicle assigned to the ride request successfully.");
    }

}
