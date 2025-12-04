package com.example.hms_backend.head_nurse.mapper;

import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.head_nurse.dto.HeadNurseDto;
import com.example.hms_backend.head_nurse.entity.HeadNurse;
import org.springframework.stereotype.Component;

//This class is for registration form
@Component
public class HeadNurseMapper {
    public HeadNurse toEntity(HeadNurseDto dto, UserEntity user) {
        HeadNurse headNurse = new HeadNurse();

        headNurse.setExperience(dto.getExperience().getLabel()); // Enum to string
        headNurse.setQualifications(dto.getQualifications());
        headNurse.setUserEntity(user);


        return headNurse;
    }
}