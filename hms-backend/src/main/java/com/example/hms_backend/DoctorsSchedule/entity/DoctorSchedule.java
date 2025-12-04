package com.example.hms_backend.DoctorsSchedule.entity;

import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.doctor.entity.Doctor;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "doctor_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 Relationship with Department
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // 🔹 Relationship with Doctor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // 🔹 Appointment fees
    private Double appointmentFees;

    // 🔹 Weekly schedule stored as separate list
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "doctor_weekly_schedule", joinColumns = @JoinColumn(name = "schedule_id"))
    private List<DaySchedule> weeklySchedule;
}