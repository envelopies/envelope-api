package ru.envelope.api.dto.user

import io.swagger.v3.oas.annotations.media.Schema

data class UserDto(
    @Schema(description = "ID пользователя")
    val id: Long,

    @Schema(description = "ник пользователя")
    val username: String,

    @Schema(description = "подтверждённая ли учётная запись")
    val verified: Boolean,

    @Schema(description = "список ролей")
    val roles: List<String>
)
