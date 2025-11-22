package com.puce.invoices.exceptions

import java.time.LocalDateTime

/**
 * Clase para respuestas de errores de validación (400 Bad Request)
 */
data class ValidationErrorResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String,
    val errors: List<FieldError>,
    val path: String? = null
)

/**
 * Representa un error en un campo específico
 */
data class FieldError(
    val field: String,
    val rejectedValue: Any?,
    val message: String
)

