package com.example.hms_backend.finance.invoice.service;

import com.example.hms_backend.finance.invoice.dto.InvoiceDTO;

public interface InvoiceService {

    //provide information for invoice form
    public InvoiceDTO sendFormData(Long patientId);


    InvoiceDTO createInvoice(InvoiceDTO dto);
}
