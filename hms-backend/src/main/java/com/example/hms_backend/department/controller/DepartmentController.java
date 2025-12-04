package com.example.hms_backend.department.controller;

import com.example.hms_backend.department.dto.DepartmentDTO;
import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.department.mapper.DepartmentMapper;
import com.example.hms_backend.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    DepartmentMapper departmentMapper;

    // ✅ Get All Departments
    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('DEPARTMENT_VIEW')")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {

        return ResponseEntity.ok(departmentService.findAll());
    }

    // ✅ Get Department by ID
    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('DEPARTMENT_VIEW')")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id) {
        DepartmentDTO dto = departmentService.findById(id);
        return ResponseEntity.ok(dto);
    }

    // ✅ Add New Department
    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('DEPARTMENT_ADD')")
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody DepartmentDTO dto) {
        Department department = departmentMapper.toEntity(dto);
        Department saved = departmentService.save(department);
        return ResponseEntity.ok(departmentMapper.toDto(saved));
    }

    // ✅ Update existing department
    @PutMapping("/update/{id}")
//    @PreAuthorize("hasAuthority('DEPARTMENT_UPDATE')")
    public ResponseEntity<Map<String, Object>> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO dto) {
        DepartmentDTO updated = departmentService.udateDepartment(id, departmentMapper.toEntity(dto));

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Department updated successfully!");
        response.put("data", updated);

        return ResponseEntity.ok(response);
    }

    // ✅ Delete Department
    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('DEPARTMENT_DELETE')")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.ok("Department deleted successfully");
    }
}
