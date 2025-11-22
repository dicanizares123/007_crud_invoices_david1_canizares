package com.puce.invoices.models.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

@Entity
@Table(name = "invoices")
data class Invoice(
    @field:NotBlank(message = "El ID del cliente es obligatorio")
    @field:Size(min = 1, max = 50, message = "El ID del cliente debe tener entre 1 y 50 caracteres")
    @Column(name = "client_id")
    val clientId: String = "",

    @field:NotBlank(message = "El nombre del cliente es obligatorio")
    @field:Size(min = 2, max = 100, message = "El nombre del cliente debe tener entre 2 y 100 caracteres")
    @Column(name = "client_name")
    val clientName: String = "",

    @field:PositiveOrZero(message = "El total antes de impuestos debe ser mayor o igual a 0")
    @Column(name = "total_before_taxes")
    val totalBeforeTaxes: Double = 0.0,

    @field:PositiveOrZero(message = "Los impuestos deben ser mayor o igual a 0")
    val taxes: Double = 0.0,

    @field:PositiveOrZero(message = "El total después de impuestos debe ser mayor o igual a 0")
    @Column(name = "total_after_taxes")
    val totalAfterTaxes: Double = 0.0,

    @OneToMany(mappedBy = "invoice")
    val invoiceDetails: List<InvoiceDetails> = listOf()
) : BaseEntity()
