package com.car_rental.car_rental_system.service.impl;

import com.car_rental.car_rental_system.dto.RideRequestDTO;
import com.car_rental.car_rental_system.entity.RideRequest;
import com.car_rental.car_rental_system.entity.User;
import com.car_rental.car_rental_system.entity.Vehicle;
import com.car_rental.car_rental_system.entity.enums.RequestStatus;
import com.car_rental.car_rental_system.exceptions.BadCredentials;
import com.car_rental.car_rental_system.exceptions.VehicleException;
import com.car_rental.car_rental_system.repo.RideRequestRepository;
import com.car_rental.car_rental_system.repo.UserRepository;
import com.car_rental.car_rental_system.repo.VehicleRepository;
import com.car_rental.car_rental_system.service.RideRequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 09:59 pm
 */

@Service
@Transactional
public class RideRequestServiceImpl implements RideRequestService {

    private RideRequestRepository repository;
    private VehicleRepository vehicleRepository;
    private UserRepository userRepository;

    public RideRequestServiceImpl(RideRequestRepository repository, VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.repository = repository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all ride requests.
     *
     * @return List of RideRequestDTO representing all ride requests
     */
    @Override
    public List<RideRequestDTO> findAll() {
        return rideListConverter(repository.findAll());
    }

    /**
     * Retrieves ride requests by status.
     *
     * @param status The status of the ride requests to retrieve
     * @return List of RideRequestDTO representing ride requests with the specified status
     */
    @Override
    public List<RideRequestDTO> findByState(RequestStatus status) {
        return rideListConverter(repository.findAllByStatus(status));
    }

    /**
     * Retrieves a ride request by its ID.
     *
     * @param id The ID of the ride request to retrieve
     * @return RideRequestDTO representing the requested ride request, or null if not found
     */
    @Override
    public RideRequestDTO findById(int id) {
        RideRequest request = repository.findById(id).orElse(null);

        if (request==null) return null;

        return rideRequestConverter(request);
    }

    /**
     * Saves a new ride request.
     *
     * @param dto The RideRequestDTO representing the ride request to be saved
     */
    @Override
    public void save(RideRequestDTO dto) {
        repository.save(rideRequestDTOConverter(dto));
    }

    /**
     * Updates an existing ride request.
     *
     * @param dto The RideRequestDTO representing the updated ride request information
     */
    @Override
    public void update(RideRequestDTO dto) {
        repository.save(rideRequestDTOConverter(dto));
    }

    /**
     * Updates the status of an existing ride request.
     *
     * @param id     The ID of the ride request to update
     * @param status The new status to set for the ride request
     * @throws RuntimeException if no ride request is found with the given ID
     */
    @Override
    public void updateStatus(int id, RequestStatus status) {
        RideRequestDTO rideRequestDTO = findById(id);
        if (rideRequestDTO==null){
            throw new RuntimeException("No Request Found");
        }

        rideRequestDTO.setStatus(status);
        repository.save(rideRequestDTOConverter(rideRequestDTO));
    }

    /**
     * Assigns a vehicle to an existing ride request.
     *
     * @param id        The ID of the ride request to update
     * @param vehicleId The ID of the vehicle to assign to the ride request
     * @throws RuntimeException if no ride request is found with the given ID
     */
    @Override
    public void assignVehicle(int id, int vehicleId) {
        RideRequestDTO rideRequestDTO = findById(id);
        if (rideRequestDTO==null){
            throw new RuntimeException("No Request Found");
        }

        rideRequestDTO.setVehicle(vehicleId);
        repository.save(rideRequestDTOConverter(rideRequestDTO));
    }

    /**
     * Converts a list of RideRequest entities to a list of RideRequestDTOs.
     *
     * @param requests The list of RideRequest entities to convert
     * @return List of RideRequestDTO representing the converted ride requests
     */
    private List<RideRequestDTO> rideListConverter(List<RideRequest> requests){
        List<RideRequestDTO> requestDTOS =new ArrayList<>();

        for (RideRequest request : requests) {
            requestDTOS.add(rideRequestConverter(request));
        }

        return requestDTOS;
    }

    /**
     * Converts a RideRequest entity to a RideRequestDTO.
     *
     * @param request The RideRequest entity to convert
     * @return RideRequestDTO representing the converted ride request
     */
    private RideRequestDTO rideRequestConverter(RideRequest request){
        return new RideRequestDTO(
                request.getReqNo(),
                request.getName(),
                request.getPickupDate(),
                request.getReturnDate(),
                request.getPickupLocation(),
                request.getDestination(),
                request.getStatus(),
                request.getCar().getVehicleId(),
                request.getUser().getUid()
        );
    }

    /**
     * Converts a RideRequestDTO to a RideRequest entity.
     *
     * @param dto The RideRequestDTO to convert
     * @return RideRequest representing the converted ride request
     * @throws BadCredentials If the user or vehicle associated with the ride request is not found
     * @throws VehicleException If the vehicle associated with the ride request is not found
     */
    private RideRequest rideRequestDTOConverter(RideRequestDTO dto){

        User user = userRepository.findById(dto.getUser()).orElse(null);
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicle()).orElse(null);

        if (user==null){
            throw new BadCredentials("No User Found");
        }

        if (vehicle==null){
            throw new VehicleException("No Vehicle Found");
        }

        return new RideRequest(
                dto.getReqNo(),
                dto.getName(),
                dto.getPickupDate(),
                dto.getReturnDate(),
                dto.getPickupLocation(),
                dto.getDestination(),
                dto.getStatus(),
                vehicle,
                user
        );
    }
}
