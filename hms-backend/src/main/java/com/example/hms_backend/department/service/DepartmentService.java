package com.example.hms_backend.department.service;

import com.example.hms_backend.department.dto.DepartmentDTO;
import com.example.hms_backend.department.dto.DepartmentViewDTO;
import com.example.hms_backend.department.entity.Department;

import java.util.List;

public interface DepartmentService {

    DepartmentDTO findById(Long id);

    Department findByName(String name);

    List<DepartmentViewDTO> findIdName();

    Department save(Department department);

    void delete(Long departmentId);

    DepartmentDTO udateDepartment(Long department_id,Department department);

    List<DepartmentDTO> findAll();
}
