package com.car_rental.car_rental_system.service.impl;

import com.car_rental.car_rental_system.dto.RideRequestDTO;
import com.car_rental.car_rental_system.dto.UserDTO;
import com.car_rental.car_rental_system.dto.VehicleDTO;
import com.car_rental.car_rental_system.entity.RideRequest;
import com.car_rental.car_rental_system.entity.User;
import com.car_rental.car_rental_system.entity.Vehicle;
import com.car_rental.car_rental_system.repo.RideRequestRepository;
import com.car_rental.car_rental_system.service.RideRequestService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 09:59 pm
 */

@Service
public class RideRequestServiceImpl implements RideRequestService {

    private RideRequestRepository repository;

    public RideRequestServiceImpl(RideRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RideRequestDTO> findAll() {
        return rideListConverter(repository.findAll());
    }

    @Override
    public List<RideRequestDTO> findByState(String status) {
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
                request.getDate(),
                request.getPickupLocation(),
                request.getDestination(),
                request.getStatus(),
                new VehicleDTO(
                        request.getCar().getVehicleId(),
                        request.getCar().getName(),
                        request.getCar().getModel(),
                        request.getCar().getPlateNumber(),
                        request.getCar().getReqDates()
                ),
                new UserDTO(
                        request.getUser().getUid(),
                        request.getUser().getName(),
                        request.getUser().getEmail(),
                        request.getUser().getUsername(),
                        request.getUser().getPassword()
                )
        );
    }

    private RideRequest rideRequestDTOConverter(RideRequestDTO dto){
        return new RideRequest(
                dto.getReqNo(),
                dto.getName(),
                dto.getDate(),
                dto.getPickupLocation(),
                dto.getDestination(),
                dto.getStatus(),
                new Vehicle(
                        dto.getVehicleDTO().getVehicleId(),
                        dto.getVehicleDTO().getName(),
                        dto.getVehicleDTO().getModel(),
                        dto.getVehicleDTO().getPlateNumber(),
                        dto.getVehicleDTO().getReqDates()
                ),
                new User(
                        dto.getUser().getUid(),
                        dto.getUser().getName(),
                        dto.getUser().getEmail(),
                        dto.getUser().getUsername(),
                        dto.getUser().getPassword()
                )
        );
    }
}
