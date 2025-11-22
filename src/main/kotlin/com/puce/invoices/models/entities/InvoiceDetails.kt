package com.puce.invoices.models.entities

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero

@Entity
@Table(name = "invoice_details")
data class InvoiceDetails(
    @field:PositiveOrZero(message = "El precio total debe ser mayor o igual a 0")
    val totalprice: Double = 0.0,

    // 1 invoice detail no puede tener muchos productos
    @field:NotNull(message = "El producto es obligatorio")
    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product? = null,

    @field:NotNull(message = "La factura es obligatoria")
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    val invoice: Invoice? = null
) : BaseEntity()
