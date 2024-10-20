package ru.envelope.api.dto

import io.swagger.v3.oas.annotations.media.Schema

data class AuthResponseDto(
    @Schema(description = "JWT этого сервиса")
    val token: String
)
