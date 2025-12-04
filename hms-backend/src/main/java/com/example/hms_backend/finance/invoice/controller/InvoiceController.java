package com.example.hms_backend.finance.invoice.controller;


import com.example.hms_backend.finance.invoice.dto.InvoiceDTO;
import com.example.hms_backend.finance.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/form-data/{patientId}")
    public ResponseEntity<InvoiceDTO> getInvoiceFormData(@PathVariable Long patientId)
    {
        return ResponseEntity.ok(invoiceService.sendFormData(patientId));
    }

    @PostMapping
    public ResponseEntity<InvoiceDTO> create(@RequestBody InvoiceDTO dto) {
        InvoiceDTO saved = invoiceService.createInvoice(dto);
        return ResponseEntity.ok(saved);
    }
}
