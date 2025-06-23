package ru.envelope.api.dto.item

import io.swagger.v3.oas.annotations.media.Schema

data class ItemDto(
    @Schema(description = "ID товара")
    val id: String,

    @Schema(description = "название товара", example = "лит энерджи")
    val title: String,

    @Schema(description = "описание товара", example = "бодрящий напиток")
    val description: String,

    @Schema(description = "цена товара в наиболее дробных единицах (копейках)", example = "12000")
    val price: Int,

    @Schema(description = "дата и время публикации", format = "yyyy-MM-dd'T'HH:mm:ss")
    val createdAt: String,

    @Schema(description = "имя пользователя, создавшего товар", example = "jointplayer")
    val username: String?,

    @Schema(description = "категория, к которой относится товар", example = "c5c6f33f-cbdd-4737-8bdb-e5c35ea03709")
    val category: String?,

    @Schema(description = "список мест доставки", example = "[\"НВК 4\", \"НВК 5\"]")
    val deliveryAddresses: List<String>,

    @Schema(description = "изображения к товару", example = "[\"28781ae4-2d90-4757-aaa3-61fe2ee9ca0e\", \"dbb8c6b2-fded-4a91-94ba-f181b3455b48\"]")
    val pictures: List<String>
)
