package com.example.hms_backend.ambulance.service;

import com.example.hms_backend.ambulance.dto.AmbulanceDTO;
import com.example.hms_backend.ambulance.entity.Ambulance;

import java.util.List;

public interface AmbulanceService {

    void saveAmbulance(AmbulanceDTO ambulanceDTO);

    List<AmbulanceDTO> findAllAmbulance();  //only id and ambulance name

    Ambulance findAmbulanceById(Long id);

    List<AmbulanceDTO> findAllAmbulanceFullObject();
}