package com.example.hms_backend.appointment.repository;

import com.example.hms_backend.appointment.dto.AppointmentDTO;
import com.example.hms_backend.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    //  RECOMMENDED: Find appointments where the 'patient' relationship has a matching ID.
    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByStatus(String status);
}
