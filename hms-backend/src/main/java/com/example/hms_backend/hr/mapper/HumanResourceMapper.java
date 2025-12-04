package com.example.hms_backend.hr.mapper;

import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.hr.dto.HumanResourceDto;
import com.example.hms_backend.hr.entity.HumanResource;
import org.springframework.stereotype.Component;

//This class is for registration form
@Component
public class HumanResourceMapper {




    public HumanResource toEntity(HumanResourceDto dto, UserEntity user) {
        HumanResource hr = new HumanResource();

        hr.setExperience(dto.getExperience().getLabel()); // Enum to string
        hr.setQualifications(dto.getQualifications());
        hr.setUserEntity(user);


        return hr;
    }
}