package com.example.hms_backend.laboratory.pathology.entity;

import com.example.hms_backend.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pathology_test_results")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathologyTestResult extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testName;
    private String resultValue;
    private String units;
    private String referenceRange;
    private Double cost;



}
