package ru.envelope.api.mappers

import ru.envelope.api.dto.user.UserDto
import ru.envelope.api.entities.User
import java.util.function.Function

object UserMapper : Function<User, UserDto> {
    override fun apply(example: User): UserDto = UserDto(
        id = example.id,
        username = example.username ?: "",
        verified = example.verified,
        roles = example.authorities.toList()
    )
}
