package com.puce.invoices.exceptions.exceptions

/**
 * Excepción lanzada cuando un recurso solicitado no es encontrado (404)
 */
class ResourceNotFoundException(
    message: String
) : RuntimeException(message)


