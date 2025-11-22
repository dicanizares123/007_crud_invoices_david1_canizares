package com.puce.invoices.models.entities

import jakarta.persistence.Column
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.GeneratedValue
import java.time.LocalDateTime

@MappedSuperclass
open class BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now()

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = LocalDateTime.now()
}
