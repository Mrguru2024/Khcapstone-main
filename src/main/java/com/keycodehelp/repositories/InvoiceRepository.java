package com.keycodehelp.repositories;

import com.keycodehelp.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // Additional custom queries can go here
    // Find all invoices by a specific user
    List<Invoice> findByUserId(Long userId);

    // Find invoices by description containing specific text
    List<Invoice> findByDescriptionContaining(String text);
}
