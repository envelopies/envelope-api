package ru.envelope.api.dto.item

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class ItemPostDto(
    @Schema(description = "название товара")
    val title: String,

    @Schema(description = "описание товара")
    val description: String,

    @Schema(description = "цена товара в наиболее дробных единицах (копейках)")
    val price: Int,

    @Schema(description = "идентификатор категории, к которой относится товар")
    val categoryId: UUID,

    @Schema(description = "список UUID мест доставки")
    val deliveryAddresses: List<UUID>?
)
