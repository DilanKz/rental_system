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
import com.car_rental.car_rental_system.util.SendMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(RideRequestServiceImpl.class);
    private RideRequestRepository repository;
    private VehicleRepository vehicleRepository;
    private UserRepository userRepository;
    private SendMail sendMail;

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
        log.info("Executing RideRequestServiceImpl findAll method");
        try {

            return rideListConverter(repository.findAll());

        } catch (Exception e) {
            log.error("Error occurred in RideRequestServiceImpl while finding all ride requests: {}", e.getMessage());
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
        log.info("Executing RideRequestServiceImpl findByState method with status: {}", status);
        try {

            return rideListConverter(repository.findAllByStatus(status));

        } catch (Exception e) {
            log.error("Error occurred in RideRequestServiceImpl while finding ride requests by status: {}", e.getMessage());
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
        log.info("Executing RideRequestServiceImpl findAllRequestsByUserId method with id: {}", id);
        try {
            User user = userRepository.findById(id).orElse(null);

            if (user==null){
                throw new BadCredentials("No user in this id");
            }

            return rideListConverter(repository.findAllByUser(user));

        } catch (Exception e) {
            log.error("Error occurred in RideRequestServiceImpl while finding all ride requests by user id: {}", id, e);
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
        log.info("Executing RideRequestServiceImpl findById method with id: {}", id);
        try {

            RideRequest request = repository.findById(id).orElse(null);

            if (request == null) return null;

            return rideRequestConverter(request);

        } catch (Exception e) {
            log.error("Error occurred in RideRequestServiceImpl while finding ride request by id: {}", id, e);
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
        log.info("Executing RideRequestServiceImpl save method with dto: {}", dto);
        try {

            repository.save(rideRequestDTOConverter(dto));

        } catch (Exception e) {
            log.error("Error occurred in RideRequestServiceImpl while saving ride request: {}", e.getMessage());
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
        log.info("Executing RideRequestServiceImpl update method with dto: {}", dto);
        try {
            RideRequest rideRequest = repository.findById(dto.getReqNo()).orElse(null);

            if (rideRequest==null){
                log.error("Error occurred in RideRequestServiceImpl while updating ride request " +
                        "couldn't find ride request in this id:{}",dto.getReqNo());
                throw new RuntimeException("No request in this id");
            }

            dto.setStatus(rideRequest.getStatus());
            dto.setUser(rideRequest.getUser().getUid());

            if (rideRequest.getVehicle()!=null){
                dto.setVehicle(rideRequest.getVehicle().getVehicleId());
            }

            repository.save(rideRequestDTOConverter(dto));

        } catch (Exception e) {
            log.error("Error occurred in RideRequestServiceImpl while updating ride request: {}", e.getMessage());
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
        log.info("Executing RideRequestServiceImpl updateStatus method with id: {}, status: {}", id, status);
        try {

            RideRequestDTO rideRequestDTO = findById(id);
            if (rideRequestDTO == null) {
                throw new RuntimeException("No Request Found");
            }

            rideRequestDTO.setStatus(status);
            RideRequest request = rideRequestDTOConverter(rideRequestDTO);
            repository.save(request);


            String userEmail = request.getUser().getEmail();
            String plateNumber = request.getVehicle().getPlateNumber();
            String emailText = "<p>Your request has been approved with the following vehicle:</p>" +
                    "<p><strong>Car Plate Number:</strong> " + plateNumber + "</p>";

            sendMail.sendEmail(userEmail, emailText);



        } catch (Exception e) {
            log.error("Error occurred in RideRequestServiceImpl while updating ride request status: {}", e.getMessage());
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
        log.info("Executing RideRequestServiceImpl assignVehicle method with id: {}, vehicleId: {}", id, vehicleId);
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
            log.error("Error occurred in RideRequestServiceImpl while assigning vehicle to ride request: {}", e.getMessage());
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
        log.info("Executing RideRequestServiceImpl findByPickupLocationAndDestination method with pickupLocation: {} and destination: {}", pickupLocation, destination);
        try {

            return rideListConverter(repository.findByPickupLocationAndDestination(pickupLocation, destination));

        } catch (Exception e) {
            log.error("Error occurred in RideRequestServiceImpl while finding ride requests by pickup location and destination: {}", e.getMessage());
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
        log.info("Executing RideRequestServiceImpl filterFromDate method with date: {}", date);
        try {

            List<RideRequest> allByPickupDate = repository.findAllByPickupDate(date);
            return rideListConverter(allByPickupDate);

        } catch (Exception e) {
            log.error("Error occurred in RideRequestServiceImpl while filtering ride requests by pickup date: {}", e.getMessage());
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
        log.info("Executing RideRequestServiceImpl filterBetweenDate method with startDate: {} and endDate: {}", startDate, endDate);
        try {

            List<RideRequest> allByPickupDate = repository.findAllByPickupDateBetween(startDate, endDate);
            return rideListConverter(allByPickupDate);
        } catch (Exception e) {
            log.error("Error occurred in RideRequestServiceImpl while filtering ride requests by pickup date range: {}", e.getMessage());
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
        log.info("Executing RideRequestServiceImpl rideListConverter method");
        try {

            List<RideRequestDTO> requestDTOS = new ArrayList<>();

            for (RideRequest request : requests) {
                requestDTOS.add(rideRequestConverter(request));
            }

            return requestDTOS;

        } catch (Exception e) {
            log.error("Error occurred in RideRequestServiceImpl while converting ride request list: {}", e.getMessage());
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
        log.info("Executing RideRequestServiceImpl  rideRequestConverter method with request: {}", request);
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
            log.error("Error occurred in RideRequestServiceImpl while converting ride request to DTO: {}", e.getMessage());
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
        log.info("Converting DTO to ride request in RideRequestServiceImpl");
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
            log.error("Error occurred in RideRequestServiceImpl while converting DTO to ride request: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


}
