package com.car_rental.car_rental_system.service.impl;

import com.car_rental.car_rental_system.dto.RideRequestDTO;
import com.car_rental.car_rental_system.entity.RideRequest;
import com.car_rental.car_rental_system.entity.User;
import com.car_rental.car_rental_system.entity.Vehicle;
import com.car_rental.car_rental_system.entity.embedded.LocationDetails;
import com.car_rental.car_rental_system.entity.enums.RequestStatus;
import com.car_rental.car_rental_system.exceptions.BadCredentials;
import com.car_rental.car_rental_system.exceptions.VehicleException;
import com.car_rental.car_rental_system.repo.RideRequestRepository;
import com.car_rental.car_rental_system.repo.UserRepository;
import com.car_rental.car_rental_system.repo.VehicleRepository;
import com.car_rental.car_rental_system.service.RideRequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public List<RideRequestDTO> findAll() {
        try {

            return rideListConverter(repository.findAll());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves ride requests by status.
     *
     * @param status The status of the ride requests to retrieve
     * @return List of RideRequestDTO representing ride requests with the specified status
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public List<RideRequestDTO> findByState(RequestStatus status) {
        try {

            return rideListConverter(repository.findAllByStatus(status));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves all ride requests associated with the specified user ID.
     *
     * @param id The ID of the user whose ride requests are to be retrieved
     * @return A list of RideRequestDTOs representing the ride requests associated with the user,
     * or an empty list if no ride requests are found for the user ID
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public List<RideRequestDTO> findAllRequestsByUserId(int id) {
        try {
            User user = userRepository.findById(id).orElse(null);

            if (user==null){
                throw new BadCredentials("No user in this id");
            }

            return rideListConverter(repository.findAllByUser(user));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves a ride request by its ID.
     *
     * @param id The ID of the ride request to retrieve
     * @return RideRequestDTO representing the requested ride request, or null if not found
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public RideRequestDTO findById(int id) {
        try {

            RideRequest request = repository.findById(id).orElse(null);

            if (request == null) return null;

            return rideRequestConverter(request);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Saves a new ride request.
     *
     * @param dto The RideRequestDTO representing the ride request to be saved
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public void save(RideRequestDTO dto) {
        try {

            repository.save(rideRequestDTOConverter(dto));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Updates an existing ride request.
     *
     * @param dto The RideRequestDTO representing the updated ride request information
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public void update(RideRequestDTO dto) {
        try {

            repository.save(rideRequestDTOConverter(dto));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Updates the status of an existing ride request.
     *
     * @param id     The ID of the ride request to update
     * @param status The new status to set for the ride request
     * @throws RuntimeException if no ride request is found with the given ID or
     *                          error occurs during the retrieval process
     */
    @Override
    public void updateStatus(int id, RequestStatus status) {
        try {

            RideRequestDTO rideRequestDTO = findById(id);
            if (rideRequestDTO == null) {
                throw new RuntimeException("No Request Found");
            }

            rideRequestDTO.setStatus(status);
            repository.save(rideRequestDTOConverter(rideRequestDTO));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Assigns a vehicle to an existing ride request.
     *
     * @param id        The ID of the ride request to update
     * @param vehicleId The ID of the vehicle to assign to the ride request
     * @throws VehicleException if no ride request is found with the given ID or if no vehicle is found with the given vehicleId,
     *                          or if the assigned vehicle is not available at the requested date
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public void assignVehicle(int id, int vehicleId) {

        try {

            RideRequest request = repository.findById(id).orElse(null);
            if (request == null) {
                throw new VehicleException("No Request Found");
            }

            Vehicle vehicle = vehicleRepository.findById(vehicleId).orElse(null);

            if (vehicle == null) {
                throw new VehicleException("no vehicle found");
            }


            if (vehicle.getReqDates().isAfter(request.getPickupDate())) {
                throw new VehicleException("This vehicle is not available at the given date");
            }

            vehicle.setReqDates(request.getReturnDate());

            request.setVehicle(vehicle);
            repository.save(request);
            vehicleRepository.save(vehicle);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves ride requests based on the specified pickup location and destination.
     *
     * @param pickupLocation The pickup location details
     * @param destination    The destination location details
     * @return List of RideRequestDTO representing ride requests matching the specified pickup location and destination
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public List<RideRequestDTO> findByPickupLocationAndDestination(LocationDetails pickupLocation, LocationDetails destination) {
        try {

            return rideListConverter(repository.findByPickupLocationAndDestination(pickupLocation, destination));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves ride requests filtered by the specified pickup date.
     *
     * @param date The pickup date to filter the ride requests
     * @return List of RideRequestDTO representing ride requests with the specified pickup date
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    @Override
    public List<RideRequestDTO> filterFromDate(LocalDate date) {
        try {

            List<RideRequest> allByPickupDate = repository.findAllByPickupDate(date);
            return rideListConverter(allByPickupDate);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves ride requests filtered by the specified pickup date range.
     *
     * @param startDate The start date of the pickup date range
     * @param endDate   The end date of the pickup date range
     * @return List of RideRequestDTO representing ride requests within the specified pickup date range
     */
    @Override
    public List<RideRequestDTO> filterBetweenDate(LocalDate startDate, LocalDate endDate) {
        try {

            List<RideRequest> allByPickupDate = repository.findAllByPickupDateBetween(startDate, endDate);
            return rideListConverter(allByPickupDate);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * Converts a list of RideRequest entities to a list of RideRequestDTOs.
     *
     * @param requests The list of RideRequest entities to convert
     * @return List of RideRequestDTO representing the converted ride requests
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    private List<RideRequestDTO> rideListConverter(List<RideRequest> requests) {
        try {

            List<RideRequestDTO> requestDTOS = new ArrayList<>();

            for (RideRequest request : requests) {
                requestDTOS.add(rideRequestConverter(request));
            }

            return requestDTOS;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Converts a RideRequest entity to a RideRequestDTO.
     *
     * @param request The RideRequest entity to convert
     * @return RideRequestDTO representing the converted ride request
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    private RideRequestDTO rideRequestConverter(RideRequest request) {

        try {

            RideRequestDTO rideRequestDTO = new RideRequestDTO();


            Vehicle vehicle = request.getVehicle();
            if (vehicle != null) {
                rideRequestDTO.setVehicle(vehicle.getVehicleId());
            }

            rideRequestDTO.setReqNo(request.getReqNo());
            rideRequestDTO.setModel(request.getModel());
            rideRequestDTO.setPickupDate(request.getPickupDate());
            rideRequestDTO.setReturnDate(request.getReturnDate());
            rideRequestDTO.setPickupLocation(request.getPickupLocation());
            rideRequestDTO.setDestination(request.getDestination());
            rideRequestDTO.setStatus(request.getStatus());
            rideRequestDTO.setUser(request.getUser().getUid());

            return rideRequestDTO;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Converts a RideRequestDTO to a RideRequest entity.
     *
     * @param dto The RideRequestDTO to convert
     * @return RideRequest representing the converted ride request
     * @throws BadCredentials   If the user or vehicle associated with the ride request is not found
     * @throws VehicleException If the vehicle associated with the ride request is not found
     * @throws RuntimeException if an error occurs during the retrieval process
     */
    private RideRequest rideRequestDTOConverter(RideRequestDTO dto) {
        try {

            User user = userRepository.findById(dto.getUser()).orElse(null);
            Vehicle vehicle = vehicleRepository.findById(dto.getVehicle()).orElse(null);

            if (user == null) {
                throw new BadCredentials("No User Found");
            }

        /*if (vehicle==null){
            throw new VehicleException("No Vehicle Found");
        }*/

            return new RideRequest(
                    dto.getReqNo(),
                    dto.getModel(),
                    dto.getPickupDate(),
                    dto.getReturnDate(),
                    dto.getPickupLocation(),
                    dto.getDestination(),
                    dto.getStatus(),
                    vehicle,
                    user
            );

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
