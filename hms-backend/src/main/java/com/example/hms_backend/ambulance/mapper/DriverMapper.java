package com.example.hms_backend.ambulance.mapper;

import com.example.hms_backend.ambulance.dto.DriverDTO;
import com.example.hms_backend.ambulance.entity.Ambulance;
import com.example.hms_backend.ambulance.entity.Driver;
import com.example.hms_backend.ambulance.service.AmbulanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {

    @Autowired
    private AmbulanceService ambulanceService;

    public DriverDTO mapDriverToDriverDTO(Driver driver)
    {
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setDriverId(driver.getId());
        driverDTO.setDriverName(driver.getDriverName());
        driverDTO.setContactNumber(driver.getContactNumber());
        driverDTO.setLicenseNumber(driver.getLicenseNumber());
        driverDTO.setAmbulanceId(driver.getAmbulance().getId());
        return driverDTO;
    }

    public Driver mapDriverDTOtoDriver(DriverDTO driverDTO)
    {
        Driver driver = new Driver();
        driver.setDriverName(driverDTO.getDriverName());
        driver.setContactNumber(driverDTO.getContactNumber());
        driver.setLicenseNumber(driverDTO.getLicenseNumber());

        Ambulance ambulance ;
        ambulance = ambulanceService.findAmbulanceById(driverDTO.getAmbulanceId());
        driver.setAmbulance(ambulance);
        return driver;
    }
}