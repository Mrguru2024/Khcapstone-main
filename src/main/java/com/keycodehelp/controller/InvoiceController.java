package com.keycodehelp.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.keycodehelp.entities.Invoice;
import com.keycodehelp.entities.User;
import com.keycodehelp.services.InvoiceService;
import com.keycodehelp.services.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final UserService userService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, UserService userService) {
        this.invoiceService = invoiceService;
        this.userService = userService;
    }

    // Display the invoice page with a list of all invoices
    @GetMapping
    public String showInvoicePage(Model model) {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        model.addAttribute("invoices", invoices);  // Pass list of invoices to the view
        model.addAttribute("newInvoice", new Invoice());  // Add new empty invoice object for the form
        return "invoices";  // Return Thymeleaf view
    }

    // Create a new invoice from the form
    @PostMapping
    public String addInvoice(@ModelAttribute("newInvoice") Invoice invoice, Principal principal, Model model) {
        // Get the current user from the Principal
        String username = principal.getName();
        Optional<User> optionalUser = userService.findByUsername(username);

        // Check if user is present
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            invoice.setUser(user);  // Associate the invoice with the logged-in user

            // Ensure paymentDate is set if not already provided
            if (invoice.getPaymentDate() == null) {
                invoice.setPaymentDate(LocalDateTime.now());
            }

            invoiceService.saveInvoice(invoice);
            return "redirect:/invoices";
        } else {
            // Handle the case where the user is not found (optional behavior, depending on your use case)
            model.addAttribute("error", "User not found");
            return "error-page";  // Return an error page or show a message
        }
    }

    // Display the form for editing an existing invoice
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id);

        // Add the invoice to the model for editing
        model.addAttribute("invoice", invoice);

        // Ensure paymentDate is set if null (optional)
        if (invoice.getPaymentDate() == null) {
            invoice.setPaymentDate(LocalDateTime.now());
        }

        return "edit-invoice";  // Return the view for editing
    }

    // Update the invoice
    @PostMapping("/update/{id}")
    public String updateInvoice(@PathVariable("id") Long id, @ModelAttribute Invoice invoice, Principal principal, Model model) {
        // Get the current user from the Principal
        String username = principal.getName();
        Optional<User> optionalUser = userService.findByUsername(username);

        // Check if user is present
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            invoice.setUser(user);  // Associate the invoice with the logged-in user

            // Ensure paymentDate is set
            if (invoice.getPaymentDate() == null) {
                invoice.setPaymentDate(LocalDateTime.now());
            }

            invoiceService.updateInvoice(id, invoice);
            return "redirect:/invoices";  // Redirect after updating
        } else {
            // Handle the case where the user is not found
            model.addAttribute("error", "User not found");
            return "error-page";  // Return an error page or show a message
        }
    }

    // Delete an invoice
    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") Long id) {
        invoiceService.deleteInvoice(id);
        return "redirect:/invoices";  // Redirect after deletion
    }
}
