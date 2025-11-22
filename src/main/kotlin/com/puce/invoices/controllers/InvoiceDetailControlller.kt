package com.puce.invoices.controllers

import com.puce.invoices.models.entities.InvoiceDetails
import com.puce.invoices.services.InvoiceDetailService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/invoices/details"])
class InvoiceDetailController(
    private val invoiceDetailService: InvoiceDetailService
) {
    @GetMapping
    fun findAll(): List<InvoiceDetails> {
        return invoiceDetailService.findAll()
    }

    @GetMapping(value = ["/{id}"])
    fun findById(@PathVariable id: Long): ResponseEntity<InvoiceDetails> {
        val invoiceDetail = invoiceDetailService.findById(id)
        return ResponseEntity.ok(invoiceDetail)
    }

    @PostMapping
    fun save(@RequestBody invoiceDetail: InvoiceDetails): ResponseEntity<InvoiceDetails> {
        val savedInvoiceDetail = invoiceDetailService.save(invoiceDetail)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInvoiceDetail)
    }

    @PutMapping(value = ["/{id}"])
    fun update(@PathVariable id: Long, @RequestBody invoiceDetail: InvoiceDetails): ResponseEntity<InvoiceDetails> {
        invoiceDetailService.findById(id) // Verifica que existe
        val updatedInvoiceDetail = invoiceDetailService.save(invoiceDetail)
        return ResponseEntity.ok(updatedInvoiceDetail)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        invoiceDetailService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}