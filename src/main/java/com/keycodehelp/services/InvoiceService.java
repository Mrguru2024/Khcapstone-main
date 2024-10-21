package com.keycodehelp.services;

import com.keycodehelp.entities.Invoice;
import com.keycodehelp.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    // Retrieve all invoices
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    // Retrieve invoice by ID with exception handling
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found with id: " + id));
    }

    // Save or create a new invoice
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    // Update an existing invoice
    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        // Fetch the existing invoice
        Invoice existingInvoice = getInvoiceById(id);

        // Update the invoice details
        existingInvoice.setAmount(invoiceDetails.getAmount());
        existingInvoice.setDescription(invoiceDetails.getDescription());
        existingInvoice.setPaymentDate(invoiceDetails.getPaymentDate());
        existingInvoice.setUser(invoiceDetails.getUser());

        return invoiceRepository.save(existingInvoice);
    }

    // Delete invoice by ID
    public void deleteInvoice(Long id) {
        Invoice invoice = getInvoiceById(id);
        invoiceRepository.delete(invoice);
    }
}
