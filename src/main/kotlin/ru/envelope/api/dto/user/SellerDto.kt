package ru.envelope.api.dto.user

import io.swagger.v3.oas.annotations.media.Schema

data class SellerDto(
    @Schema(description = "ID продавца")
    val id: Long,

    @Schema(description = "его имя в телеграмм")
    val name: String
)
