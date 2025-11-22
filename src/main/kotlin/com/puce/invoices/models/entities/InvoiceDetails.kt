package com.puce.invoices.models.entities

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn


@Entity
@Table(name = "invoice_details")
data class  InvoiceDetails (
    val totalprice : Double = 0.0,

    // 1 invoice detail no puede tener muchos productos
    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product? = null,

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    val invoice: Invoice? = null,

): BaseEntity()