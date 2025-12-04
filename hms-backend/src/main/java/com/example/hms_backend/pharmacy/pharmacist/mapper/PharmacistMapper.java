package com.example.hms_backend.pharmacy.pharmacist.mapper;

import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.pharmacy.pharmacist.dto.PharmacistDto;
import com.example.hms_backend.pharmacy.pharmacist.entity.Pharmacist;
import org.springframework.stereotype.Component;

//This class is for registration form
@Component
public class PharmacistMapper {

    public Pharmacist toEntity(PharmacistDto dto, UserEntity user) {
        Pharmacist pharmacist = new Pharmacist();

        pharmacist.setExperience(dto.getExperience().getLabel()); // Enum to string
        pharmacist.setQualifications(dto.getQualifications());
        pharmacist.setUserEntity(user);


        return pharmacist;
    }

}
