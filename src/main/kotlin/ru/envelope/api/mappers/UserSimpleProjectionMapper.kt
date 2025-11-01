package ru.envelope.api.mappers

import ru.envelope.api.dto.user.SellerDto
import ru.envelope.api.projections.UserSimpleProjection
import java.util.function.Function

object UserSimpleProjectionMapper: Function<UserSimpleProjection, SellerDto> {
    override fun apply(projection: UserSimpleProjection): SellerDto = SellerDto(
        id = projection.getId(),
        name = projection.getName()
    )
}
