// Kotlin
package com.puce.invoices.models.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.OneToMany

@Entity
@Table(name = "invoices")
data class Invoice(
    @Column(name = "client_id")
    val clientId: String = "",
    @Column(name = "client_name")
    val clientName: String = "",
    @Column(name = "total_before_taxes")
    val totalBeforeTaxes: Double = 0.0,
    val taxes: Double = 0.0,
    @Column(name = "total_after_taxes")
    val totalAfterTaxes: Double = 0.0,
    @OneToMany(mappedBy = "invoice")
    val invoiceDetails: List<InvoiceDetails> = listOf()
) : BaseEntity()
