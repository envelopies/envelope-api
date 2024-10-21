package ru.envelope.api.dto.item

import io.swagger.v3.oas.annotations.media.Schema

data class ItemDto(
    @Schema(description = "ID товара")
    val id: String,

    @Schema(description = "название товара")
    val title: String,

    @Schema(description = "описание товара")
    val description: String,

    @Schema(description = "цена товара в наиболее дробных единицах (копейках)")
    val price: Int,

    @Schema(description = "дата и время публикации", format = "yyyy-MM-dd'T'HH:mm:ss")
    val createdAt: String,
)
