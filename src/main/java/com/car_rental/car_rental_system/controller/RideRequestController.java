package com.car_rental.car_rental_system.controller;

import com.car_rental.car_rental_system.dto.RideRequestDTO;
import com.car_rental.car_rental_system.entity.enums.RequestStatus;
import com.car_rental.car_rental_system.service.RideRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity getRequestOnStatus(@PathVariable String status){

        // Get all requests based on the status

        return ResponseEntity.ok("");
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

        return ResponseEntity.ok("Request status has been updated successfully.");
    }

}
