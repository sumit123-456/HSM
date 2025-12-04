package com.example.hms_backend.DeathCertificate.Service;

import com.example.hms_backend.DeathCertificate.dto.DeathCertificateDTO;
import com.example.hms_backend.patient.dto.PatientDto;
import com.example.hms_backend.patient.dto.PatientIpdViewDTO;
import com.example.hms_backend.patient.dto.PatientViewDto;

import java.util.List;
import java.util.Map;

public interface DeathCertificateService {
    DeathCertificateDTO create(DeathCertificateDTO dto);
    List<DeathCertificateDTO> getAll();
    DeathCertificateDTO getById(Long id);



    DeathCertificateDTO updateDeathCertificate(DeathCertificateDTO dto,Long id);


}
