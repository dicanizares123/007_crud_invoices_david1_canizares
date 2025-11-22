package com.puce.invoices.exceptions

import com.puce.invoices.exceptions.exceptions.InvoiceEntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
open class GlobalExceptionHandler {

    @ExceptionHandler(InvoiceEntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: InvoiceEntityNotFoundException): ResponseEntity<String?> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
    }
}
