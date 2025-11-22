package com.puce.invoices.repositories

import com.puce.invoices.models.entities.Invoice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository : JpaRepository<Invoice, Long> {
}