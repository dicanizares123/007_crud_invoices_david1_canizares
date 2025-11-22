package com.puce.invoices.services

import com.puce.invoices.exceptions.exceptions.InvoiceEntityNotFoundException
import com.puce.invoices.models.entities.Product
import com.puce.invoices.repositories.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService (
    private val productRepository: ProductRepository
) {
    fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    fun findById(id: Long): Product {
        val product = productRepository.findById(id)
        if (product.isPresent) {
            return product.get()
        }
        throw InvoiceEntityNotFoundException("Product with id $id not found")
    }

    fun save(product: Product): Product {
        return productRepository.save(product)
    }

}