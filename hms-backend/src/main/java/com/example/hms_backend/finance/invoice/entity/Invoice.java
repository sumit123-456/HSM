package com.example.hms_backend.finance.invoice.entity;

import com.example.hms_backend.finance.invoice.entity.childEntity.InvoiceDoctor;
import com.example.hms_backend.finance.invoice.entity.childEntity.InvoiceMedicine;
import com.example.hms_backend.finance.invoice.entity.childEntity.InvoiceRoom;
import com.example.hms_backend.finance.invoice.entity.childEntity.InvoiceTest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;

    private Double totalAmount;
    private String paymentMethod;
    private String paymentStatus;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<InvoiceDoctor> doctors;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<InvoiceMedicine> medicines;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<InvoiceRoom> rooms;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<InvoiceTest> tests;
}
