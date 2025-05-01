package ru.envelope.api.mappers

import ru.envelope.api.dto.user.UserDto
import ru.envelope.api.projections.UserProjection
import java.util.function.Function

object UsersProjectionMapper: Function<List<UserProjection>, UserDto> {
    override fun apply(projections: List<UserProjection>): UserDto {
        val example = projections.first()
        return UserDto(
            id = example.getId(),
            username = example.getUsername(),
            fullname = example.getFullname(),
            verified = example.isVerified(),
            roles = projections.map { it.getAuthority() }
        )
    }
}
