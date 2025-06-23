package ru.envelope.api.dto.item

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class ItemPutDto(
    @Schema(description = "название товара", example = "лит энерджи")
    val title: String?,

    @Schema(description = "описание товара", example = "бодрящий напиток")
    val description: String?,

    @Schema(description = "цена товара в наиболее дробных единицах (копейках)", example = "12000")
    val price: Int?,

    @Schema(description = "опубликован ли товар на сайте (доступен только в админке)", example = "true")
    val published: Boolean?,

    @Schema(description = "удалён ли товар с сайта", example = "false")
    val removed: Boolean?,

    @Schema(description = "идентификатор категории, к которой относится товар", example = "c5c6f33f-cbdd-4737-8bdb-e5c35ea03709")
    val categoryId: UUID?,

    @Schema(description = "список UUID мест доставки", example = "[\"73425643-23d2-4188-b32c-214f3a688411\", \"8b0a31f1-0158-4397-9dc2-a7a5c1243589\"]")
    val deliveryAddresses: List<UUID>?,

    @Schema(description = "список UUID картинок", example = "[\"28781ae4-2d90-4757-aaa3-61fe2ee9ca0e\", \"dbb8c6b2-fded-4a91-94ba-f181b3455b48\"]")
    val pictures: List<UUID>?
)
