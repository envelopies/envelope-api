package ru.envelope.api.mappers

import ru.envelope.api.dto.item.ItemDto
import ru.envelope.api.projections.ItemProjection
import ru.envelope.api.util.format
import java.util.function.Function

object ItemProjectionMapper: Function<List<ItemProjection>, ItemDto> {
    override fun apply(projections: List<ItemProjection>): ItemDto {
        val projection = projections.first()
        return ItemDto(
            id = projection.getId().toString(),
            title = projection.getTitle(),
            description = projection.getDescription(),
            price = projection.getPrice(),
            quantity = projection.getQuantity(),
            unit = projection.getUnit(),
            createdAt = projection.getCreatedAt().format(),
            username = projection.getUsername(),
            category = projection.getCategory(),
            deliveryAddresses = projections
                .mapNotNull { it.getDeliveryAddress() }
                .toList(),
            pictures = projections
                .mapNotNull { it.getPictureId() }
                .map { "https://nvk1.store/images/${it}.webp" }
                .toList()
        )
    }
}
