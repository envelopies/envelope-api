package ru.envelope.api.mappers

import ru.envelope.api.dto.location.LocationDto
import ru.envelope.api.entities.Location
import java.util.function.Function

object LocationMapper: Function<Location, LocationDto> {
    override fun apply(entity: Location): LocationDto {
        return LocationDto(
            id = entity.id.toString(),
            title = entity.title,
            latitude = entity.latitude?.toDouble(),
            longitude = entity.longitude?.toDouble()
        )
    }
}
