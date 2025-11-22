package com.puce.invoices.controllers

import com.puce.invoices.models.entities.Product
import com.puce.invoices.services.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/invoices/products"])
class ProductController(
    private val productService: ProductService
) {
    @GetMapping
    fun findAll(): List<Product> {
        return productService.findAll()
    }

    @GetMapping(value = ["/{id}"])
    fun findById(@PathVariable id: Long): ResponseEntity<Product> {
        val product = productService.findById(id)
        return ResponseEntity.ok(product)
    }

    @PostMapping
    fun save(@RequestBody product: Product): ResponseEntity<Product> {
        val savedProduct = productService.save(product)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct)
    }
}