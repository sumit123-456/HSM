package com.example.hms_backend.laboratory.laboratorist.mapper;


import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.laboratory.laboratorist.dto.LaboratoristDto;
import com.example.hms_backend.laboratory.laboratorist.entity.Laboratorist;
import org.springframework.stereotype.Component;

//This class is for registration form
@Component
public class LaboratoristMapper {

    public Laboratorist toEntity(LaboratoristDto dto, UserEntity user) {
        Laboratorist laboratorist = new Laboratorist();

        laboratorist.setExperience(dto.getExperience().getLabel()); // Enum to string
        laboratorist.setQualifications(dto.getQualifications());
        laboratorist.setLaboratoryType(dto.getLaboratoryType());   // Enum to enum
        laboratorist.setUserEntity(user);



        return laboratorist;
    }

}
