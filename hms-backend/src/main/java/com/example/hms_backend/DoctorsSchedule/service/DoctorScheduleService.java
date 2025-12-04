package com.example.hms_backend.DoctorsSchedule.service;

import com.example.hms_backend.DoctorsSchedule.dto.DoctorScheduleDTO;

import java.util.List;

public interface DoctorScheduleService {

    DoctorScheduleDTO createSchedule(DoctorScheduleDTO dto);

    DoctorScheduleDTO updateSchedule(Long id, DoctorScheduleDTO dto);

    List<DoctorScheduleDTO> getAllSchedules();

    DoctorScheduleDTO getScheduleById(Long id);

    void deleteSchedule(Long id);

    String getStatusById(Long id);
}