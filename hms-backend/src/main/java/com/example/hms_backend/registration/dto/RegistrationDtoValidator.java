package com.example.hms_backend.registration.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@Component
public class RegistrationDtoValidator implements Validator {
    private final SpringValidatorAdapter validatorAdapter;

    @Autowired
    public RegistrationDtoValidator(jakarta.validation.Validator beanValidator) {
        this.validatorAdapter = new SpringValidatorAdapter(beanValidator);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationDto dto = (RegistrationDto) target;

//      Validate top-level fields like username, email, firstName, etc.
        validatorAdapter.validate(dto, errors);

        if (dto.getRoleId() == null)
        {
            errors.rejectValue("roleId", "NotNull", "Role is required");
            return;
        }


        switch (dto.getRoleId().intValue())
        {
            case 3: // Doctor
                if (dto.getDoctorDto() == null) {
                    errors.rejectValue("doctorDto", "NotNull", "Doctor details are required");
                } else {
                    errors.pushNestedPath("doctorDto");
                    validatorAdapter.validate(dto.getDoctorDto(), errors);
                    errors.popNestedPath();

                }
                break;

            case 6: // Accountant
                if (dto.getAccountantDto() == null) {
                    errors.rejectValue("accountantDto", "NotNull", "Accountant details are required");
                } else {
                    errors.pushNestedPath("accountantDto");
                    validatorAdapter.validate(dto.getAccountantDto(), errors);
                    errors.popNestedPath();
                }
                break;

            case 8: // Laboratorist
                if (dto.getLaboratoristDto() == null) {
                    errors.rejectValue("laboratoristDto", "NotNull", "Laboratorist details are required");
                } else {
                    errors.pushNestedPath("laboratoristDto");
                    validatorAdapter.validate(dto.getLaboratoristDto(), errors);
                    errors.popNestedPath();
                }
                break;

            case 4: // Head Nurse
                if (dto.getHeadNurseDto() == null) {
                    errors.rejectValue("headNurseDto", "NotNull", "Head Nurse details are required");
                } else {
                    errors.pushNestedPath("headNurseDto");
                    validatorAdapter.validate(dto.getHeadNurseDto(), errors);
                    errors.popNestedPath();
                }
                break;

            case 10: // Receptionist
                if (dto.getReceptionistDto() == null) {
                    errors.rejectValue("receptionistDto", "NotNull", "Receptionist details are required");
                } else {
                    errors.pushNestedPath("receptionistDto");
                    validatorAdapter.validate(dto.getReceptionistDto(), errors);
                    errors.popNestedPath();
                }
                break;

            case 5: // Pharmacist
                if (dto.getPharmacistDto() == null) {
                    errors.rejectValue("pharmacistDto", "NotNull", "Pharmacist details are required");
                } else {
                    errors.pushNestedPath("pharmacistDto");
                    validatorAdapter.validate(dto.getPharmacistDto(), errors);
                    errors.popNestedPath();
                }
                break;

            case 7: // Human Resource
                if (dto.getHumanResourceDto() == null) {
                    errors.rejectValue("humanResourceDto", "NotNull", "HR details are required");
                } else {
                    errors.pushNestedPath("humanResourceDto");
                    validatorAdapter.validate(dto.getHumanResourceDto(), errors);
                    errors.popNestedPath();
                }
                break;

            case 9: // Insurer
                if (dto.getInsurerDto() == null) {
                    errors.rejectValue("insurerDto", "NotNull", "Insurance details are required");
                } else {
                    errors.pushNestedPath("insurerDto");
                    validatorAdapter.validate(dto.getInsurerDto(), errors);
                    errors.popNestedPath();

                }
                break;

            default:
                errors.rejectValue("roleId", "Invalid", "Unsupported role selected");
        }
    }

}

