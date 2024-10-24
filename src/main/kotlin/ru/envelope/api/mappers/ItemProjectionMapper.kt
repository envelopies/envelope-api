package ru.envelope.api.mappers

import ru.envelope.api.dto.item.ItemDto
import ru.envelope.api.projections.ItemProjection
import ru.envelope.api.util.localDateTimeFormatter
import java.time.ZoneOffset
import java.util.function.Function

object ItemProjectionMapper: Function<ItemProjection, ItemDto> {
    override fun apply(projection: ItemProjection): ItemDto = ItemDto(
        id = projection.getId().toString(),
        title = projection.getTitle(),
        description = projection.getDescription(),
        price = projection.getPrice(),
        createdAt = projection.getCreatedAt().atZone(ZoneOffset.UTC).format(localDateTimeFormatter),
        username = projection.getUsername(),
    )
}
