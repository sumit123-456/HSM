package com.example.hms_backend.finance.invoice.entity.childEntity;

import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.finance.invoice.entity.Invoice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "invoice_doctor")
public class InvoiceDoctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;  // Existing table

    private Double fee;     // Snapshot
}
