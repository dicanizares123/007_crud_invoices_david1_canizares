package com.puce.invoices.services

import com.puce.invoices.exceptions.exceptions.BadRequestException
import com.puce.invoices.exceptions.exceptions.InvoiceEntityNotFoundException
import com.puce.invoices.models.entities.InvoiceDetails
import com.puce.invoices.repositories.InvoiceDetailRepository
import org.springframework.stereotype.Service

@Service
class InvoiceDetailService(
    private val invoiceDetailRepository: InvoiceDetailRepository
) {
    fun findAll(): List<InvoiceDetails> {
        return invoiceDetailRepository.findAll()
    }

    fun findById(id: Long): InvoiceDetails {
        if (id <= 0) {
            throw BadRequestException("El ID del detalle de factura debe ser un número positivo")
        }
        val invoiceDetail = invoiceDetailRepository.findById(id)
        if (invoiceDetail.isPresent) {
            return invoiceDetail.get()
        }
        throw InvoiceEntityNotFoundException("Detalle de factura con id $id no encontrado")
    }

    fun save(invoiceDetail: InvoiceDetails): InvoiceDetails {
        // Validaciones de negocio
        if (invoiceDetail.product == null) {
            throw BadRequestException("El producto es obligatorio en el detalle de factura")
        }
        if (invoiceDetail.invoice == null) {
            throw BadRequestException("La factura es obligatoria en el detalle")
        }
        if (invoiceDetail.totalprice < 0) {
            throw BadRequestException("El precio total no puede ser negativo")
        }
        return invoiceDetailRepository.save(invoiceDetail)
    }

    fun deleteById(id: Long) {
        if (id <= 0) {
            throw BadRequestException("El ID del detalle de factura debe ser un número positivo")
        }
        if (!invoiceDetailRepository.existsById(id)) {
            throw InvoiceEntityNotFoundException("Detalle de factura con id $id no encontrado")
        }
        invoiceDetailRepository.deleteById(id)
    }
}