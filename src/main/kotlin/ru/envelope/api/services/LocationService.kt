package ru.envelope.api.services

import org.springframework.data.domain.Sort
import ru.envelope.api.dto.location.LocationDto
import ru.envelope.api.dto.location.LocationPostDto
import ru.envelope.api.dto.location.LocationPutDto
import java.util.UUID

interface LocationService {
    fun getLocations(
        pageNumber: Int,
        pageSize: Int,
        sortField: String,
        sortOrder: Sort.Direction
    ): List<LocationDto>

    fun getLocation(id: UUID): LocationDto?

    fun createLocation(
        locationDto: LocationPostDto
    ): LocationDto

    fun updateLocation(
        id: UUID,
        locationDto: LocationPutDto
    ): LocationDto

    fun deleteLocation(id: UUID)
}
