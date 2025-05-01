package ru.envelope.api.dto.user

import io.swagger.v3.oas.annotations.media.Schema

data class UserPutDto(
    @Schema(description = "подтверждённая ли учётная запись")
    val verified: Boolean?,

    @Schema(description = "список ролей")
    val roles: List<String>?
)
