package com.puce.invoices.models.entities

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

@Entity
@Table(name = "products")
data class Product(
    @field:NotBlank(message = "El nombre del producto es obligatorio")
    @field:Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    val name: String = "",

    @field:Positive(message = "El precio debe ser mayor a 0")
    val price: Double = 0.0,

    // 1 producto tiene muchos invoice details
    @OneToMany(mappedBy = "product")
    val invoiceDetails: List<InvoiceDetails> = listOf()
) : BaseEntity()


