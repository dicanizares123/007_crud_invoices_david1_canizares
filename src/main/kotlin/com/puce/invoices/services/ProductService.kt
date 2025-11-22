package com.puce.invoices.services

import com.puce.invoices.exceptions.exceptions.BadRequestException
import com.puce.invoices.exceptions.exceptions.InvoiceEntityNotFoundException
import com.puce.invoices.models.entities.Product
import com.puce.invoices.repositories.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    fun findById(id: Long): Product {
        if (id <= 0) {
            throw BadRequestException("El ID del producto debe ser un número positivo")
        }
        val product = productRepository.findById(id)
        if (product.isPresent) {
            return product.get()
        }
        throw InvoiceEntityNotFoundException("Producto con id $id no encontrado")
    }

    fun save(product: Product): Product {
        // Validaciones adicionales de negocio
        if (product.name.isBlank()) {
            throw BadRequestException("El nombre del producto no puede estar vacío")
        }
        if (product.price < 0) {
            throw BadRequestException("El precio del producto no puede ser negativo")
        }
        if (product.price == 0.0) {
            throw BadRequestException("El precio del producto debe ser mayor a 0")
        }
        return productRepository.save(product)
    }

}