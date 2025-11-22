package com.puce.invoices.models.entities
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.OneToMany
@Entity
@Table(name = "products")
data class Product (
    val name: String = "",
    val price: Double = 0.0,
    //1 producto tiene muchos invoice details


    @OneToMany(mappedBy = "product")
    val invoiceDetails: List<InvoiceDetails> = listOf(),
):BaseEntity()


