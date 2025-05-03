package ru.envelope.api.services

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.envelope.api.dto.location.LocationDto
import ru.envelope.api.dto.location.LocationPostDto
import ru.envelope.api.dto.location.LocationPutDto
import ru.envelope.api.entities.Location
import ru.envelope.api.exceptions.BadSortFieldException
import ru.envelope.api.exceptions.LocationNotFoundException
import ru.envelope.api.mappers.LocationMapper
import ru.envelope.api.repositories.LocationRepository
import ru.envelope.api.services.ItemServiceV1.Companion
import java.math.BigDecimal
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class LocationServiceV1(
    private val locationRepository: LocationRepository
): LocationService {
    companion object {
        val allowedSortFields = setOf("title")
    }

    override fun getLocations(pageNumber: Int, pageSize: Int, sortField: String, sortOrder: Sort.Direction): List<LocationDto> {
        val pageRequest = getPageRequest(pageNumber, pageSize, sortField, sortOrder)
        return locationRepository.findAll(pageRequest)
            .map(LocationMapper)
            .toList()
    }

    override fun getLocation(id: UUID): LocationDto? {
        val location = locationRepository.findById(id).getOrNull() ?: return null
        return LocationMapper.apply(location)
    }

    override fun createLocation(locationDto: LocationPostDto): LocationDto {
        val location = locationRepository.save(Location(
            title = locationDto.title,
            latitude = locationDto.latitude?.toBigDecimal(),
            longitude = locationDto.longitude?.toBigDecimal()
        ))

        return getLocation(location.id)!!
    }

    override fun updateLocation(id: UUID, locationDto: LocationPutDto): LocationDto {
        val locationEntity = locationRepository.findById(id)

        if (locationEntity.isEmpty) {
            throw LocationNotFoundException(id)
        }

        val location = locationEntity.get()

        if (locationDto.title != null) {
            location.title = locationDto.title
        }

        if (locationDto.latitude != null) {
            location.latitude = locationDto.latitude.toBigDecimal()
        }

        if (locationDto.longitude != null) {
            location.longitude = locationDto.longitude.toBigDecimal()
        }

        locationRepository.save(location)

        return getLocation(location.id)!!
    }

    override fun deleteLocation(id: UUID) {
        val locationEntity = locationRepository.findById(id)

        if (locationEntity.isPresent) {
            val location = locationEntity.get()
            location.removed = true
            locationRepository.save(location)
        }
    }

    private fun getPageRequest(pageNumber: Int, pageSize: Int, sortField: String, sortOrder: Sort.Direction): PageRequest {
        if (!allowedSortFields.contains(sortField)) {
            throw BadSortFieldException(allowedSortFields)
        }
        return PageRequest.of(pageNumber, pageSize, Sort.by(sortOrder, sortField))
    }
}
