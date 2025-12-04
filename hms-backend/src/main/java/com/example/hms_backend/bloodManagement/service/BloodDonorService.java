package com.example.hms_backend.bloodManagement.service;


import com.example.hms_backend.bloodManagement.dtos.DonorRequestDTO;
import com.example.hms_backend.bloodManagement.dtos.DonorResponseDTO;

import java.util.List;

public interface BloodDonorService {

    DonorResponseDTO createDonor(DonorRequestDTO donorRequestDTO);
    DonorResponseDTO getDonorById(String donorId);
    List<DonorResponseDTO> getAllDonors();
    List<DonorResponseDTO> getActiveDonors();
    List<DonorResponseDTO> getDonorsByBloodGroup(String bloodGroup);
    DonorResponseDTO updateDonor(Long donorId, DonorRequestDTO donorRequestDTO);
    void deactivateDonor(Long donorId);
    void activateDonor(Long donorId);
    boolean isPhoneNumberExists(String phone);
    boolean isEmailExists(String email);
    List<DonorResponseDTO> searchDonors(String searchTerm);
    Long getDonorCountByBloodGroup(String bloodGroup);
}
