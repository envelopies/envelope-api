package ru.envelope.api.dto

data class ItemDto(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val createdAt: String,
)
