package com.example.hms_backend.bloodManagement.service;


import com.example.hms_backend.bloodManagement.dtos.BloodDonationRequestDTO;
import com.example.hms_backend.bloodManagement.dtos.BloodDonationResponceDTO;

import java.util.List;

public interface BloodDonationService {
    BloodDonationResponceDTO createDonation(BloodDonationRequestDTO requestDTO);
    List<BloodDonationResponceDTO> getDonationsByDonor(String donorId);
    List<BloodDonationResponceDTO> getAllDonations();
    void storeDonation(String donationId);
    void processDonation(String donationId);
    BloodDonationResponceDTO getDonationById(Long id);

}
