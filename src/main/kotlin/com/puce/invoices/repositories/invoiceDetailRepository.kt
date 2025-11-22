package com.puce.invoices.repositories

import com.puce.invoices.models.entities.InvoiceDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceDetailRepository : JpaRepository<InvoiceDetails, Long>

/**
 * Para la capa de repo: @Repository
 *
 * Para  la capa de servicio: @Service
 *
 * Para la capa de controlador: @Controller  o @RestController
 *
 *
 *
 *
 */