package com.example.hms_backend.DoctorsSchedule.mapper;

import com.example.hms_backend.DoctorsSchedule.dto.DayScheduleDTO;
import com.example.hms_backend.DoctorsSchedule.dto.DoctorScheduleDTO;
import com.example.hms_backend.DoctorsSchedule.entity.DaySchedule;
import com.example.hms_backend.DoctorsSchedule.entity.DoctorSchedule;
import com.example.hms_backend.DoctorsSchedule.service.DoctorScheduleService;
import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.doctor.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DoctorScheduleMapper {




    public DoctorScheduleDTO toDTO(DoctorSchedule entity) {
        if (entity == null) return null;

        return DoctorScheduleDTO.builder()
                .id(entity.getId())
                .departmentId(entity.getDepartment().getId())
                .departmentName(entity.getDepartment().getDepartment_name())
                .doctorName(entity.getDoctor().getUserEntity().getUserInfo().getFirstName()
                        +" "+entity.getDoctor().getUserEntity().getUserInfo().getLastName())
                .doctorId(entity.getDoctor().getId())
                .appointmentFees(entity.getAppointmentFees())
                .weeklySchedule(
                        entity.getWeeklySchedule().stream()
                                .map(this::toDayScheduleDTO)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public DoctorSchedule toEntity(DoctorScheduleDTO dto, Department department, Doctor doctor) {
        if (dto == null) return null;

        DoctorSchedule entity = new DoctorSchedule();
        entity.setId(dto.getId());
        entity.setDepartment(department);
        entity.setDoctor(doctor);

        entity.setAppointmentFees(dto.getAppointmentFees());
        entity.setWeeklySchedule(
                dto.getWeeklySchedule().stream()
                        .map(this::toDaySchedule)
                        .collect(Collectors.toList())
        );
        return entity;
    }

    private DayScheduleDTO toDayScheduleDTO(DaySchedule daySchedule) {
        return DayScheduleDTO.builder()
                .day(daySchedule.getDay())
                .startTime(daySchedule.getStartTime())
                .endTime(daySchedule.getEndTime())
                .build();
    }

    public DaySchedule toDaySchedule(DayScheduleDTO dto) {
        return DaySchedule.builder()
                .day(dto.getDay())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }
}
