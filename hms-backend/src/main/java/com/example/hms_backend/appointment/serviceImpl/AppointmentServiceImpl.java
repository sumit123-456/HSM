package com.example.hms_backend.appointment.serviceImpl;

import com.example.hms_backend.appointment.dto.AppointmentDTO;
import com.example.hms_backend.appointment.entity.Appointment;
import com.example.hms_backend.appointment.entity.AppointmentStatus;
import com.example.hms_backend.appointment.mapper.AppointmentMapper;
import com.example.hms_backend.appointment.repository.AppointmentRepository;
import com.example.hms_backend.appointment.service.AppointmentService;
import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.department.repo.DepartmentRepo;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.doctor.repo.DoctorRepo;
import com.example.hms_backend.patient.entity.Patient;
import com.example.hms_backend.patient.repo.PatientRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final DepartmentRepo departmentRepo;
    private final AppointmentMapper appointmentMapper;

    @Override
    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        System.out.println("➡ Creating Appointment from DTO: " + dto);

        Patient patient = null;
        Doctor doctor = null;
        Department department = null;

        if (dto.getPatientId()!= null) {
                    patient = patientRepo.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found: " + dto.getPatientId()));
        }

        if (dto.getDoctorId()!= null)
        {
            doctor = doctorRepo.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found: " + dto.getDoctorId()));
        }


        if (dto.getDepartmentId()!= null)
        {
            department = departmentRepo.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found: " + dto.getDepartmentId()));
        }

        Appointment appointment = appointmentMapper.convertToEntity(dto,doctor,patient,department);

        Appointment saved = appointmentRepo.save(appointment);
        System.out.println("✅ Appointment created with ID: " + saved.getId());

        AppointmentDTO savedDTO = appointmentMapper.convertToDTO(saved);


        return savedDTO;
    }

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepo.findAll().stream()
                .map(appointmentMapper::convertToDTO) // Assuming convertToDto is the correct method
                .collect(Collectors.toList()); // Collect the resulting DTOs into a List
    }

    @Override
    public AppointmentDTO getAppointment(Long id) {
        return appointmentMapper.convertToDTO( appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found: " + id)));
    }

    @Override
    @Transactional
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto) {

        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found: " + id));

        if (dto.getPatientId() != null) {
            Patient patient = patientRepo.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found: " + dto.getPatientId()));
            appointment.setPatient(patient);
        }

        if (dto.getDoctorId() != null) {
            Doctor doctor = doctorRepo.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found: " + dto.getDoctorId()));
            appointment.setDoctor(doctor);
        }

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepo.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found: " + dto.getDepartmentId()));
            appointment.setDepartment(department);
        }

        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setStatus(dto.getStatus());
        appointment.setSymptoms(dto.getSymptoms());

        Appointment saved = appointmentRepo.save(appointment);
        System.out.println("Appointment updated, id=" + saved.getId());
        return appointmentMapper.convertToDTO(saved);
    }

    @Override
    @Transactional
    public void deleteAppointment(Long id) {
        appointmentRepo.deleteById(id);
    }

    @Override
    public List<AppointmentDTO> getAppointmentSbyPatientId(Long patientId) {
       List<AppointmentDTO> ap = appointmentRepo.findByPatientId(patientId).stream().map
                (appointment -> appointmentMapper.convertToDTO(appointment))
                .collect(Collectors.toList());
        return ap;
    }

    @Override
    public String updateAppointmentStatus(Long id, String status) {
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found: " + id));
        appointment.setStatus(AppointmentStatus.valueOf(status));
        appointmentRepo.save(appointment);
        return "Status Updated Successfully!";
    }
}
