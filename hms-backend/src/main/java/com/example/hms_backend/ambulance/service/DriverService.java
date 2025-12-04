package com.example.hms_backend.ambulance.service;

import com.example.hms_backend.ambulance.dto.DriverDTO;
import com.example.hms_backend.ambulance.entity.Driver;

import java.util.List;

public interface DriverService {

    void saveDriver(DriverDTO driverDTO);

    Driver findDriverById(Long id);

    List<DriverDTO> findAllDriver();  //send only id and name

    List<DriverDTO> findAllDriverFullObject(); // send full data
}
