package com.example.hms_backend.finance.invoice.entity.childEntity;

import com.example.hms_backend.finance.invoice.entity.Invoice;
import com.example.hms_backend.pharmacy.medicine.entity.Medicine;
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
@Table(name = "invoice_medicine")
public class InvoiceMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;  // Existing table

    private int qty;
    private Double pricePerUnit;
    private Double totalPrice;
}

