package ru.envelope.api.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.UUID

@Entity
data class Item(
    @Id
    val id: UUID,
    val title: String,
    val description: String,
    val price: BigDecimal,
    val createdAt: ZonedDateTime,
)
