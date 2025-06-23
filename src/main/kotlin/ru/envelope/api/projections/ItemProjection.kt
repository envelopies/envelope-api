package ru.envelope.api.projections

import java.time.Instant
import java.util.*

interface ItemProjection {
    fun getId(): UUID?
    fun getTitle(): String
    fun getDescription(): String
    fun getPrice(): Int
    fun getQuantity(): Int
    fun getUnit(): String
    fun getCreatedAt(): Instant
    fun getUsername(): String
    fun getCategory(): String
    fun getDeliveryAddress(): String?
    fun getPictureId(): String?
}
