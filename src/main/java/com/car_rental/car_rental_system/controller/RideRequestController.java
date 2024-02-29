package com.car_rental.car_rental_system.controller;

import com.car_rental.car_rental_system.dto.RideRequestDTO;
import com.car_rental.car_rental_system.entity.embedded.LocationDetails;
import com.car_rental.car_rental_system.entity.enums.RequestStatus;
import com.car_rental.car_rental_system.service.RideRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author Dilan
 * @created 28/02/2024 - 10:47 am
 */

@RestController
@CrossOrigin
@RequestMapping("/request")
public class RideRequestController {

    private RideRequestService requestService;

    public RideRequestController(RideRequestService requestService) {
        this.requestService = requestService;
    }


    /**
     * Retrieves all ride requests.
     *
     * @return ResponseEntity with status OK and a list of all ride requests
     */
    @GetMapping
    public ResponseEntity getAllRequests(){

        return ResponseEntity.ok(requestService.findAll());
    }

    /**
     * Retrieves a single ride request by its ID.
     *
     * @param id The ID of the ride request to retrieve
     * @return ResponseEntity with status OK and the requested ride request, or NOT_FOUND if the ID is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity getOneRequest(@PathVariable int id){

        RideRequestDTO requestDTO = requestService.findById(id);

        if (requestDTO==null){
            return ResponseEntity.badRequest().body("No Request found for the given id");
        }

        return ResponseEntity.ok(requestDTO);

    }


    /**
     * Retrieves ride requests based on their status.
     *
     * @param status The status to filter ride requests
     * @return ResponseEntity with status OK and a list of ride requests with the specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity getRequestOnStatus(@PathVariable RequestStatus status){

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
    public ResponseEntity getRequestByLocation(@RequestBody List<LocationDetails> locationDetailsList){

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
    public ResponseEntity getRequestByDate(@RequestParam LocalDate date){
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
    public ResponseEntity getRequestByDates(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate){

        return ResponseEntity.ok(requestService.filterBetweenDate(startDate,endDate));
    }

    /**
     * Adds a new ride request.
     *
     * @param dto The DTO containing the details of the new ride request
     * @return ResponseEntity with status OK and the newly created ride request, or BAD_REQUEST if the request is invalid
     */
    @PostMapping
    public ResponseEntity addRequest(@RequestBody RideRequestDTO dto){

        requestService.save(dto);
        return ResponseEntity.ok("Request sent successfully");
    }


    /**
     * Updates an existing ride request.
     * @param dto The DTO containing the updated details of the ride request
     * @return ResponseEntity with status OK and the updated ride request, or BAD_REQUEST if the request is invalid
     */
    @PutMapping
    public ResponseEntity updateRequest(@RequestBody RideRequestDTO dto){

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
    public ResponseEntity updateRequestStatus(@PathVariable int id, @RequestParam RequestStatus status) {
        //Update request status to approve or reject
        requestService.updateStatus(id,status);

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
    public ResponseEntity assignVehicle(@PathVariable int id, @RequestParam int vehicleId) {
        //assign vehicle to a request
        requestService.assignVehicle(id,vehicleId);

        return ResponseEntity.ok("Vehicle assigned to the ride request successfully.");
    }

}
