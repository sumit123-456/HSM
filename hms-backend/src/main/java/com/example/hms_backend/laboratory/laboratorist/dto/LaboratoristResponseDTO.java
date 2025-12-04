package com.example.hms_backend.laboratory.laboratorist.dto;

import com.example.hms_backend.enums.Enums;
import lombok.Data;

@Data
public class LaboratoristResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String experience;
    private String qualification;
    private Enums.Gender gender;

    public LaboratoristResponseDTO(Long id, String name, String email, String phoneNumber, String address, String experience, String qualification,Enums.Gender gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.experience = experience;
        this.qualification = qualification;
        this.gender = gender;
    }
}
