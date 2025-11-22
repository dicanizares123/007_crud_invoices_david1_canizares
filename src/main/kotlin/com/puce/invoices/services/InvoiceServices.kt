package com.puce.invoices.services

import com.puce.invoices.exceptions.exceptions.InvoiceEntityNotFoundException
import com.puce.invoices.models.entities.Invoice
import com.puce.invoices.repositories.InvoiceRepository
import org.springframework.stereotype.Service

@Service
class InvoiceServices(
    private val invoiceRepository: InvoiceRepository
) {
    fun findAll(): List<Invoice> {
        return invoiceRepository.findAll()
    }

    fun findById(id: Long): Invoice {
        val invoice = invoiceRepository.findById(id)
        if (invoice.isPresent) {
            return invoice.get()
        }
        throw InvoiceEntityNotFoundException("Invoice with id $id not found")
    }

    fun save(invoice: Invoice): Invoice {
        return invoiceRepository.save(invoice)
    }

    fun deleteById(id: Long) {
        if (!invoiceRepository.existsById(id)) {
            throw InvoiceEntityNotFoundException("Invoice with id $id not found")
        }
        invoiceRepository.deleteById(id)
    }
}