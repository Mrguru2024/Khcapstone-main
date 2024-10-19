package com.keycodehelp.services;

import com.keycodehelp.entities.Invoice;
import com.keycodehelp.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    // Constructor-based injection
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    invoice.setDescription(invoiceDetails.getDescription());
                    invoice.setAmount(invoiceDetails.getAmount());
                    invoice.setPaymentDate(invoiceDetails.getPaymentDate());
                    return invoiceRepository.save(invoice);
                }).orElseThrow(() -> new RuntimeException("Invoice not found with id " + id));
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}
