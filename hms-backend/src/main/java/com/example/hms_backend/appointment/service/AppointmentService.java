package com.example.hms_backend.appointment.service;

import com.example.hms_backend.appointment.dto.AppointmentDTO;
import com.example.hms_backend.appointment.entity.Appointment;

import java.util.List;

public interface AppointmentService {

    AppointmentDTO createAppointment(AppointmentDTO dto);

    List<AppointmentDTO> getAllAppointments();

    AppointmentDTO getAppointment(Long id);

    AppointmentDTO updateAppointment(Long id, AppointmentDTO dto);

    void deleteAppointment(Long id);

    List<AppointmentDTO> getAppointmentSbyPatientId(Long patientId);

    String updateAppointmentStatus(Long id, String status);
}
