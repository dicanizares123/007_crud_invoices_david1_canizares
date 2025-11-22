package com.puce.invoices.services

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
        val invoiceDetail = invoiceDetailRepository.findById(id)
        if (invoiceDetail.isPresent) {
            return invoiceDetail.get()
        }
        throw InvoiceEntityNotFoundException("Invoice detail with id $id not found")
    }

    fun save(invoiceDetail: InvoiceDetails): InvoiceDetails {
        return invoiceDetailRepository.save(invoiceDetail)
    }

    fun deleteById(id: Long) {
        if (!invoiceDetailRepository.existsById(id)) {
            throw InvoiceEntityNotFoundException("Invoice detail with id $id not found")
        }
        invoiceDetailRepository.deleteById(id)
    }
}