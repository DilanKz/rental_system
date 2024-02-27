package com.car_rental.car_rental_system.service.impl;

import com.car_rental.car_rental_system.dto.VehicleDTO;
import com.car_rental.car_rental_system.entity.Vehicle;
import com.car_rental.car_rental_system.repo.VehicleRepository;
import com.car_rental.car_rental_system.service.VehicleService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dilan
 * @created 27/02/2024 - 09:10 pm
 */
public class VehicleServiceImpl implements VehicleService {

    private VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<VehicleDTO> findAll() {

        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<VehicleDTO> list = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            list.add(new VehicleDTO(vehicle.getVehicleId(), vehicle.getName(), vehicle.getModel(), vehicle.getPlateNumber(), vehicle.getReqDates()));
        }

        return list;
    }

    @Override
    public VehicleDTO findById(int id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElse(null);

        if (vehicle==null){
            return null;
        }

        return new VehicleDTO(vehicle.getVehicleId(), vehicle.getName(), vehicle.getModel(), vehicle.getPlateNumber(), vehicle.getReqDates());
    }

    @Override
    public void save(VehicleDTO dto) {

    }

    @Override
    public void update(VehicleDTO dto) {

    }

    @Override
    public VehicleDTO findByPlateNumber(String plateNumber) {
        Vehicle vehicle = vehicleRepository.findByPlateNumber(plateNumber).orElse(null);

        if (vehicle==null){
            return null;
        }

        return new VehicleDTO(vehicle.getVehicleId(), vehicle.getName(), vehicle.getModel(), vehicle.getPlateNumber(), vehicle.getReqDates());
    }

    @Override
    public VehicleDTO findByModel(String model) {
        Vehicle vehicle = vehicleRepository.findByModel(model).orElse(null);

        if (vehicle==null){
            return null;
        }

        return new VehicleDTO(vehicle.getVehicleId(), vehicle.getName(), vehicle.getModel(), vehicle.getPlateNumber(), vehicle.getReqDates());
    }
}
