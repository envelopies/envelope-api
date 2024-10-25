package ru.envelope.api.dto.item

import io.swagger.v3.oas.annotations.media.Schema

data class ItemPutDto(
    @Schema(description = "название товара")
    val title: String?,

    @Schema(description = "описание товара")
    val description: String?,

    @Schema(description = "цена товара в наиболее дробных единицах (копейках)")
    val price: Int?,

    @Schema(description = "опубликован ли товар на сайте")
    val published: Boolean?,

    @Schema(description = "удалён ли товар с сайта")
    val removed: Boolean?
)
