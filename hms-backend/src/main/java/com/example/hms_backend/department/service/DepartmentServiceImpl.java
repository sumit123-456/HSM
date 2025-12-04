package com.example.hms_backend.department.service;

import com.example.hms_backend.department.dto.DepartmentDTO;
import com.example.hms_backend.department.dto.DepartmentViewDTO;
import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.department.mapper.DepartmentMapper;
import com.example.hms_backend.department.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    DepartmentMapper departmentMapper;


    @Override
    public DepartmentDTO findById(Long department_id)
    {
        Department department = departmentRepo.findById(department_id)
                .orElseThrow(()-> new RuntimeException("Department Not Found With Id :- "+department_id));

        return departmentMapper.toDto(department);

    }

    @Override
    public Department findByName(String dept_name) {
        return departmentRepo.findDepartmentByName(dept_name);
    }

    @Override
    public List<DepartmentViewDTO> findIdName() {
        return departmentRepo.findAll().stream()
                .map(dep -> new DepartmentViewDTO(
                        dep.getId(),
                        dep.getDepartment_name()
                )).toList();
    }

    @Override
    public Department save(Department department) {
        return departmentRepo.save(department);
    }

    @Override
    @Transactional
    public void delete(Long departmentId) {
        departmentRepo.deleteById(departmentId);
    }

    @Override
    public DepartmentDTO udateDepartment(Long department_id,Department department) {
        department.setId(department_id);
        departmentRepo.save(department);
        return departmentMapper.toDto(department);
    }

    @Override
    public List<DepartmentDTO> findAll() {
        return departmentRepo.findAll().stream()
                .map(department -> departmentMapper.toDto(department)).toList();
    }
}

