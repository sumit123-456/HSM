package com.example.hms_backend.laboratory.laboratorist.service;

import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.laboratory.laboratorist.dto.LaboratoristResponseDTO;
import com.example.hms_backend.laboratory.laboratorist.repo.LaboratoristRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LaboratoristService {

    @Autowired
    LaboratoristRepo laboratoristRepo;

    public List<LaboratoristResponseDTO> getAllLaboratorists()
    {
        return laboratoristRepo.findAll().stream().map
                (l-> new LaboratoristResponseDTO(
                        l.getId(),
                        l.getUserEntity().getUserInfo().getFirstName()+" "+l.getUserEntity().getUserInfo().getLastName(),
                        l.getUserEntity().getEmail(),
                        l.getUserEntity().getUserInfo().getMobileNumber(),
                        l.getUserEntity().getUserInfo().getAddress().getAddressLine1()+", "+l.getUserEntity().getUserInfo().getAddress().getAddressLine2()+", "+
                                l.getUserEntity().getUserInfo().getAddress().getCity()+", "+
                                l.getUserEntity().getUserInfo().getAddress().getState()+", "+
                                l.getUserEntity().getUserInfo().getAddress().getCountry(),
                        l.getExperience(),
                        l.getQualifications(),
                        l.getUserEntity().getUserInfo().getGender()

                )).collect(Collectors.toList());
    }

    //return all Pathalogy Technicians

    public List<LaboratoristResponseDTO> getAllPathalogyTechnician()
    {
        return laboratoristRepo.findAll().stream()
                .filter(l->l.getLaboratoryType()== Enums.LaboratoryType.PATHLAB).map
                (l-> new LaboratoristResponseDTO(
                        l.getId(),
                        l.getUserEntity().getUserInfo().getFirstName()+" "+l.getUserEntity().getUserInfo().getLastName(),
                        l.getUserEntity().getEmail(),
                        l.getUserEntity().getUserInfo().getMobileNumber(),
                        l.getUserEntity().getUserInfo().getAddress().getAddressLine1()+", "+l.getUserEntity().getUserInfo().getAddress().getAddressLine2()+", "+
                                l.getUserEntity().getUserInfo().getAddress().getCity()+", "+
                                l.getUserEntity().getUserInfo().getAddress().getState()+", "+
                                l.getUserEntity().getUserInfo().getAddress().getCountry(),
                        l.getExperience(),
                        l.getQualifications(),
                        l.getUserEntity().getUserInfo().getGender()

                )).collect(Collectors.toList());
    }

    public List<LaboratoristResponseDTO> getAllRadiologyTechnician()
    {
        return laboratoristRepo.findAll().stream()
                .filter(l->l.getLaboratoryType()== Enums.LaboratoryType.RADIOLOGY).map
                        (l-> new LaboratoristResponseDTO(
                                l.getId(),
                                l.getUserEntity().getUserInfo().getFirstName()+" "+l.getUserEntity().getUserInfo().getLastName(),
                                l.getUserEntity().getEmail(),
                                l.getUserEntity().getUserInfo().getMobileNumber(),
                                l.getUserEntity().getUserInfo().getAddress().getAddressLine1()+", "+l.getUserEntity().getUserInfo().getAddress().getAddressLine2()+", "+
                                        l.getUserEntity().getUserInfo().getAddress().getCity()+", "+
                                        l.getUserEntity().getUserInfo().getAddress().getState()+", "+
                                        l.getUserEntity().getUserInfo().getAddress().getCountry(),
                                l.getExperience(),
                                l.getQualifications(),
                                l.getUserEntity().getUserInfo().getGender()

                        )).collect(Collectors.toList());
    }

}
