package com.puce.invoices.controllers

import com.puce.invoices.models.entities.Invoice
import com.puce.invoices.services.InvoiceServices
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/invoices"])
class InvoiceController(
    private val invoiceServices: InvoiceServices
) {
    @GetMapping
    fun findAll(): List<Invoice> {
        return invoiceServices.findAll()
    }

    @GetMapping(value = ["/{id}"])
    fun findById(@PathVariable id: Long): ResponseEntity<Invoice> {
        val invoice = invoiceServices.findById(id)
        return ResponseEntity.ok(invoice)
    }

    @PostMapping
    fun save(@Valid @RequestBody invoice: Invoice): ResponseEntity<Invoice> {
        val savedInvoice = invoiceServices.save(invoice)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInvoice)
    }

    @PutMapping(value = ["/{id}"])
    fun update(@PathVariable id: Long, @Valid @RequestBody invoice: Invoice): ResponseEntity<Invoice> {
        invoiceServices.findById(id) // Verifica que existe
        val updatedInvoice = invoiceServices.save(invoice)
        return ResponseEntity.ok(updatedInvoice)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        invoiceServices.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}