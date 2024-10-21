package ru.envelope.api.projections

import java.time.ZonedDateTime
import java.util.*

interface ItemProjection {
    fun getId(): UUID?
    fun getTitle(): String
    fun getDescription(): String
    fun getPrice(): Int
    fun getCreatedAt(): ZonedDateTime
    fun getUsername(): String
}
