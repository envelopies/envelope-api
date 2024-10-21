package ru.envelope.api.mappers

import ru.envelope.api.dto.item.ItemDto
import ru.envelope.api.entities.Item
import java.time.format.DateTimeFormatter
import java.util.function.Function

object ItemMapper: Function<Item, ItemDto> {
    private val localDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    override fun apply(item: Item): ItemDto = ItemDto(
        id = item.id?.toString() ?: "",
        title = item.title,
        description = item.description,
        price = item.price,
        createdAt = item.createdAt.format(localDateTimeFormatter),
        username = null
    )
}
