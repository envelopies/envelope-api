package ru.envelope.api.dto.item

import io.swagger.v3.oas.annotations.media.Schema

data class ItemIdDto(
    @Schema(description = "ID товара")
    val id: String
)
