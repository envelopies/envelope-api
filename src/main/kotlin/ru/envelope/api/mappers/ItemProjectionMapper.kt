package ru.envelope.api.mappers

import ru.envelope.api.dto.item.ItemDto
import ru.envelope.api.projections.ItemProjection
import java.time.format.DateTimeFormatter
import java.util.function.Function

object ItemProjectionMapper: Function<ItemProjection, ItemDto> {
    private val localDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    override fun apply(projection: ItemProjection): ItemDto = ItemDto(
        id = projection.getId().toString(),
        title = projection.getTitle(),
        description = projection.getDescription(),
        price = projection.getPrice(),
        createdAt = projection.getCreatedAt().format(localDateTimeFormatter),
        username = projection.getUsername(),
    )
}
