package com.puce.invoices.exceptions.exceptions

/**
 * Excepción lanzada cuando una entidad de factura no es encontrada (404)
 */
class InvoiceEntityNotFoundException(
    message: String
) : RuntimeException(message)
