package com.keycodehelp.services;

import com.keycodehelp.entities.Invoice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    public Invoice saveInvoice(Invoice invoice) {
        return invoice;
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return Optional.empty();
    }

    public List<Invoice> getAllInvoices() {
        return List.of();
    }

    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        return invoiceDetails;
    }

    public void deleteInvoice(Long id) {
    }
    // Service logic here
}
