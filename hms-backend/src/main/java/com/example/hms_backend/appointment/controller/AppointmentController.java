package com.example.hms_backend.appointment.controller;

import com.example.hms_backend.appointment.dto.AppointmentDTO;
import com.example.hms_backend.appointment.entity.Appointment;
import com.example.hms_backend.appointment.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDTO> create(@RequestBody AppointmentDTO dto, HttpServletRequest req) {
        System.out.println("Content-Type header: " + req.getHeader("Content-Type"));
        System.out.println(" BODY RECEIVED: " + dto);
        AppointmentDTO saved = appointmentService.createAppointment(dto);

        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAll() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointment(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> update(@PathVariable Long id, @RequestBody AppointmentDTO dto) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("Appointment deleted successfully");
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDTO>> getByPatientId(@PathVariable Long patientId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentSbyPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/status/{id}/{status}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @PathVariable String status) {
        String response = appointmentService.updateAppointmentStatus(id, status);
        return ResponseEntity.ok(response);
    }
}
