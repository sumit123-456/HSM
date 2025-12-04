package com.example.hms_backend.department.mapper;

import com.example.hms_backend.department.dto.DepartmentDTO;
import com.example.hms_backend.department.entity.Department;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentMapper {

    public DepartmentDTO toDto(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setDepartment_name(department.getDepartment_name());
        dto.setDescription(department.getDescription());
        dto.setDepartment_head(department.getDepartment_head());

        // extract only doctor names
        List<String> doctorNames = department.getDoctors()
                .stream()
                .map(doc->doc.getUserEntity().getUserInfo().getFirstName()+" "+doc.getUserEntity().getUserInfo().getLastName())
                .toList();
        dto.setDoctors(doctorNames);

        return dto;
    }

    public Department toEntity(DepartmentDTO dto) {
        Department department = new Department();
        department.setId(dto.getId());
        department.setDepartment_name(dto.getDepartment_name());
        department.setDepartment_head(dto.getDepartment_head());
        department.setDescription(dto.getDescription());
        // doctors will be set separately if needed
        return department;
    }
}

