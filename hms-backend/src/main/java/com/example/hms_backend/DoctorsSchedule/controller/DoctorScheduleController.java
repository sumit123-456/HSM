package com.example.hms_backend.DoctorsSchedule.controller;

import com.example.hms_backend.DoctorsSchedule.dto.DoctorScheduleDTO;
import com.example.hms_backend.DoctorsSchedule.entity.DoctorSchedule;
import com.example.hms_backend.DoctorsSchedule.service.DoctorScheduleService;
import com.example.hms_backend.customResponse.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor-schedule")
public class DoctorScheduleController {

    @Autowired
    private DoctorScheduleService doctorScheduleService;


    @PostMapping()
    public ResponseEntity<ApiResponse<DoctorScheduleDTO>> addDoctorSchedule(@RequestBody DoctorScheduleDTO dto)
    {
        DoctorScheduleDTO saved = doctorScheduleService.createSchedule(dto);
        ApiResponse<DoctorScheduleDTO> response = new ApiResponse<>("Doctor Schedule Created Successfully!",saved);
        return ResponseEntity.ok(response);
    }




    @GetMapping()
    public ResponseEntity<ApiResponse<List<DoctorScheduleDTO>>> getDoctorSchedule()
    {
        List<DoctorScheduleDTO> scheduleDTOList = doctorScheduleService.getAllSchedules();
        ApiResponse<List<DoctorScheduleDTO>> reponse = new ApiResponse<>("Doctor Schedule List",scheduleDTOList);
        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorScheduleDTO>> getDoctorScheduleById(@PathVariable Long id)
    {
        DoctorScheduleDTO scheduleDTO = doctorScheduleService.getScheduleById(id);
        ApiResponse<DoctorScheduleDTO> reponse = new ApiResponse<>("Doctor Schedule List",scheduleDTO);
        return ResponseEntity.ok(reponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorScheduleDTO>> updateDoctorSchedule(@PathVariable Long id ,@RequestBody DoctorScheduleDTO dto)
    {
        DoctorScheduleDTO updated = doctorScheduleService.updateSchedule(id, dto);
        ApiResponse<DoctorScheduleDTO> response = new ApiResponse<>("Doctor Schedule Updated Successfully!",updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctorSchedule(@PathVariable Long id)
    {
        doctorScheduleService.deleteSchedule(id);
        return ResponseEntity.ok("Doctor Schedule Deleted Successfully!");
    }

}
