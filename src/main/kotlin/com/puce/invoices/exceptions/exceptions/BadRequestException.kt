package com.puce.invoices.exceptions.exceptions

/**
 * Excepción lanzada cuando hay errores de validación de negocio (400)
 */
class BadRequestException(
    message: String
) : RuntimeException(message)

