package ru.envelope.api.projections

import java.time.Instant
import java.util.*

interface ItemProjection {
    fun getId(): UUID?
    fun getTitle(): String
    fun getDescription(): String
    fun getPrice(): Int
    fun getCreatedAt(): Instant
    fun getUsername(): String
}
