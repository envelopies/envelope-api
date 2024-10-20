package ru.envelope.api.mappers

import ru.envelope.api.dto.ItemDto
import ru.envelope.api.entities.Item
import java.time.format.DateTimeFormatter
import java.util.function.Function

object ItemMapper: Function<Item, ItemDto> {
    override fun apply(item: Item): ItemDto = ItemDto(
        title = item.title,
        description = item.description,
        price = item.price.toDouble(),
        createdAt = item.createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    )
}
