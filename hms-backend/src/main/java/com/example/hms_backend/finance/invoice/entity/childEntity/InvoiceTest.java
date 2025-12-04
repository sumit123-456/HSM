package com.example.hms_backend.finance.invoice.entity.childEntity;

import com.example.hms_backend.finance.invoice.entity.Invoice;
import com.example.hms_backend.laboratory.laboratorist.entity.Test;
import com.example.hms_backend.laboratory.pathology.entity.PathologyReport;
import com.example.hms_backend.laboratory.radiology.entity.RadiologyReport;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "invoice_test")
public class InvoiceTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    private Double price;
    private Integer quantity;
    private Double totalPrice;

   @OneToMany
   @JoinColumn(name = "Path_reoprt_id")
   private List<PathologyReport> pathologyReport;

   @OneToMany
   @JoinColumn(name = "radiology_report_id")
   private List<RadiologyReport> radiologyReport;

}
