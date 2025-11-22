package com.puce.invoices.services

import com.puce.invoices.exceptions.exceptions.BadRequestException
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
        if (id <= 0) {
            throw BadRequestException("El ID de la factura debe ser un número positivo")
        }
        val invoice = invoiceRepository.findById(id)
        if (invoice.isPresent) {
            return invoice.get()
        }
        throw InvoiceEntityNotFoundException("Factura con id $id no encontrada")
    }

    fun save(invoice: Invoice): Invoice {
        // Validaciones de negocio
        if (invoice.clientId.isBlank()) {
            throw BadRequestException("El ID del cliente es obligatorio")
        }
        if (invoice.clientName.isBlank()) {
            throw BadRequestException("El nombre del cliente es obligatorio")
        }
        if (invoice.totalBeforeTaxes < 0 || invoice.taxes < 0 || invoice.totalAfterTaxes < 0) {
            throw BadRequestException("Los montos de la factura no pueden ser negativos")
        }
        // Validación de coherencia de totales
        val expectedTotal = invoice.totalBeforeTaxes + invoice.taxes
        if (Math.abs(expectedTotal - invoice.totalAfterTaxes) > 0.01) {
            throw BadRequestException("El total después de impuestos no coincide con la suma del subtotal más impuestos")
        }
        return invoiceRepository.save(invoice)
    }

    fun deleteById(id: Long) {
        if (id <= 0) {
            throw BadRequestException("El ID de la factura debe ser un número positivo")
        }
        if (!invoiceRepository.existsById(id)) {
            throw InvoiceEntityNotFoundException("Factura con id $id no encontrada")
        }
        invoiceRepository.deleteById(id)
    }
}