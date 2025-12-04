package com.example.hms_backend.receptionist.mapper;

import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.receptionist.dto.ReceptionistDto;
import com.example.hms_backend.receptionist.entity.Receptionist;
import org.springframework.stereotype.Component;

//This class is for registration form
@Component
public class ReceptionistMapper {

    public Receptionist toEntity(ReceptionistDto dto, UserEntity user) {
        Receptionist receptionist = new Receptionist();

        receptionist.setExperience(dto.getExperience().getLabel()); // Enum to string
        receptionist.setQualifications(dto.getQualifications());
        receptionist.setUserEntity(user);


        return receptionist;

    }
}

