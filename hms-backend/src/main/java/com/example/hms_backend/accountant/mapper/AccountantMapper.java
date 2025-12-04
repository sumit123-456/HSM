package com.example.hms_backend.accountant.mapper;

import com.example.hms_backend.accountant.dto.AccountantDto;
import com.example.hms_backend.accountant.entity.Accountant;
import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.enums.Enums;
import org.springframework.stereotype.Component;

@Component
public class AccountantMapper {

    public Accountant toEntity(AccountantDto dto, UserEntity user) {
        Accountant accountant = new Accountant();

        accountant.setExperience(dto.getExperience().getLabel()); // Enum to string
        accountant.setQualifications(dto.getQualifications());

        accountant.setUserEntity(user);

        return accountant;

    }
}
