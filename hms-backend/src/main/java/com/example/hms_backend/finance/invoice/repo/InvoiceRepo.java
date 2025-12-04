package com.example.hms_backend.finance.invoice.repo;

import com.example.hms_backend.finance.invoice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepo extends JpaRepository<Invoice,Long> {
}
