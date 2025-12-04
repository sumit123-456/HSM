package com.example.hms_backend.appointment.entity;

import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;


    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private String symptoms;
}
