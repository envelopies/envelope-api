package ru.envelope.api.dto.location

import io.swagger.v3.oas.annotations.media.Schema

data class LocationDto(
    @Schema(description = "ID места")
    val id: String,

    @Schema(description = "название места")
    val title: String,

    @Schema(description = "широта", nullable = true)
    val latitude: Double?,

    @Schema(description = "долгота", nullable = true)
    val longitude: Double?,
)
