package ru.envelope.api.dto.location

import io.swagger.v3.oas.annotations.media.Schema

data class LocationPostDto(
    @Schema(description = "название места")
    val title: String,

    @Schema(description = "широта")
    val latitude: Double?,

    @Schema(description = "долгота")
    val longitude: Double?,
)
