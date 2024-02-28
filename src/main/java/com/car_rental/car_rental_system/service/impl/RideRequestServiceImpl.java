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

    @Override
    public List<RideRequestDTO> findAll() {
        return rideListConverter(repository.findAll());
    }

    @Override
    public List<RideRequestDTO> findByState(RequestStatus status) {
        return rideListConverter(repository.findAllByStatus(status));
    }

    @Override
    public RideRequestDTO findById(int id) {
        RideRequest request = repository.findById(id).orElse(null);

        if (request==null) return null;

        return rideRequestConverter(request);
    }

    @Override
    public void save(RideRequestDTO dto) {
        repository.save(rideRequestDTOConverter(dto));
    }

    @Override
    public void update(RideRequestDTO dto) {
        repository.save(rideRequestDTOConverter(dto));
    }

    private List<RideRequestDTO> rideListConverter(List<RideRequest> requests){
        List<RideRequestDTO> requestDTOS =new ArrayList<>();

        for (RideRequest request : requests) {
            requestDTOS.add(rideRequestConverter(request));
        }

        return requestDTOS;
    }

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
