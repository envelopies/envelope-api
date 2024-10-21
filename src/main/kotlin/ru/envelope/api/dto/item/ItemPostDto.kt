package ru.envelope.api.dto.item

import io.swagger.v3.oas.annotations.media.Schema

data class ItemPostDto(
    @Schema(description = "название товара")
    val title: String,

    @Schema(description = "описание товара")
    val description: String,

    @Schema(description = "цена товара в наиболее дробных единицах (копейках)")
    val price: Int,
)
