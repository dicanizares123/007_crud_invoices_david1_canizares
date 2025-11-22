package com.puce.invoices.exceptions

import java.time.LocalDateTime

/**
 * Clase para respuestas de error estructuradas
 */
data class ErrorResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String,
    val path: String? = null
)


