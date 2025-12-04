package com.example.hms_backend.appointment.mapper;

import com.example.hms_backend.appointment.dto.AppointmentDTO;
import com.example.hms_backend.appointment.entity.Appointment;
import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.patient.entity.Patient;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    private final GenericHttpMessageConverter genericHttpMessageConverter;

    public AppointmentMapper(GenericHttpMessageConverter genericHttpMessageConverter) {
        this.genericHttpMessageConverter = genericHttpMessageConverter;
    }

    public Appointment convertToEntity(AppointmentDTO appointmentDTO, Doctor doctor, Patient patient, Department department) {
        Appointment appointment = new Appointment();

        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
        appointment.setStatus(appointmentDTO.getStatus());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDepartment(department);
        appointment.setSymptoms(appointmentDTO.getSymptoms());
        return appointment;
    }

    public AppointmentDTO convertToDTO(Appointment appointment) {
        AppointmentDTO appointmentDTO = new AppointmentDTO();

        // convert appointment entity to appointment DTO
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setAppointmentDate(appointment.getAppointmentDate());
        appointmentDTO.setAppointmentTime(appointment.getAppointmentTime());
        appointmentDTO.setStatus(appointment.getStatus());
        appointmentDTO.setSymptoms(appointment.getSymptoms());
        appointmentDTO.setDoctorName(appointment.getDoctor().getUserEntity().getUserInfo().getFirstName()+" "+ appointment.getDoctor().getUserEntity().getUserInfo().getLastName());
        appointmentDTO.setPatientName(appointment.getPatient().getFirstName()+" "+appointment.getPatient().getLastName());
        appointmentDTO.setPatientAge(appointment.getPatient().getAge());
        appointmentDTO.setPatientContact(appointment.getPatient().getContactInfo());
        appointmentDTO.setPatientHospitalId(appointment.getPatient().getPatientHospitalId());


        // If appointment has patient & doctor entity & department entity, set their IDs in DTO
        if (appointment.getPatient() != null) {
            appointmentDTO.setPatientId(appointment.getPatient().getId());
        }
        if (appointment.getDoctor() != null) {
            appointmentDTO.setDoctorId(appointment.getDoctor().getId());
        }
        if (appointment.getDepartment() != null)
        {
            appointmentDTO.setDepartmentId(appointment.getDepartment().getId());
        }
        return appointmentDTO;
    }

}

