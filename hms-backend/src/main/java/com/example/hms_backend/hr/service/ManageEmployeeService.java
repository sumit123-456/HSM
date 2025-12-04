package com.example.hms_backend.hr.service;

import com.example.hms_backend.hr.dto.EmployeeUpdateDTO;
import com.example.hms_backend.hr.dto.EmployeeResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ManageEmployeeService {

//    List<Map<String, Object>> getUsersByRole(Long roleId);

    String updateStatus(Long userId, String status);

    public List<EmployeeResponseDTO> getUsersByRole(Long roleId);

    public EmployeeResponseDTO updateEmployee(Long userId,EmployeeUpdateDTO dto, MultipartFile idProofPic);
}
