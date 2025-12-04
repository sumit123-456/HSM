package com.example.hms_backend.DoctorsSchedule.controller;

import com.example.hms_backend.department.dto.DepartmentDTO;
import com.example.hms_backend.department.dto.DepartmentViewDTO;
import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.department.service.DepartmentService;
import com.example.hms_backend.doctor.dto.DoctorViewDTO;
import com.example.hms_backend.doctor.service.DoctorService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor-schedule")
public class DoctorScheduleHelpingController {

    @Autowired
    DepartmentService departmentService;


    @Autowired
    DoctorService doctorService;

    @GetMapping("/departments")
    public List<DepartmentViewDTO> getDepartmentIdName()
    {
       return departmentService.findIdName();
    }

    @GetMapping("/doctors/{deptId}")
    public List<DoctorViewDTO> getDoctorByDepartment(@PathVariable Long deptId)
    {
        return doctorService.findDoctorByDepartmentId(deptId);
    }


}
