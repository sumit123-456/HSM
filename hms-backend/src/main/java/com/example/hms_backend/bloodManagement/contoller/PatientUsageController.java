package com.example.hms_backend.bloodManagement.contoller;


import com.example.hms_backend.bloodManagement.model.PatientUsage;
import com.example.hms_backend.bloodManagement.repo.PatientUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usage")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PatientUsageController {
    private final PatientUsageRepository usageRepo;

    @GetMapping
    public ResponseEntity<List<PatientUsage>> getAllHistory() {
        List<PatientUsage> history = usageRepo.findAll(Sort.by(Sort.Direction.DESC, "usedAt"));
        return ResponseEntity.ok(history);
    }
}
