package ru.envelope.api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import ru.envelope.api.dto.location.LocationDto
import ru.envelope.api.dto.location.LocationPostDto
import ru.envelope.api.dto.location.LocationPutDto
import ru.envelope.api.services.LocationService
import java.util.UUID

@Tag(name = "locations (v1)", description = "работа с местами доставки")
@RestController
@RequestMapping(value = ["v1/locations"], produces = [MediaType.APPLICATION_JSON_VALUE])
class LocationControllerV1(
    private val locationService: LocationService
) {
    @Operation(summary = "получение всего списка мест")
    @GetMapping
    fun getLocations(
        @RequestParam("pageNumber", required = false, defaultValue = "0") pageNumber: Int,
        @RequestParam("pageSize", required = false, defaultValue = "10") pageSize: Int,
        @RequestParam("sortField", required = false, defaultValue = "title") sortField: String,
        @RequestParam("sortDirection", required = false, defaultValue = "ASC") sortDirection: Sort.Direction,
    ): List<LocationDto> {
        return locationService.getLocations(pageNumber, pageSize, sortField, sortDirection)
    }

    @Operation(summary = "получение всей информации о конкретном месте")
    @GetMapping("{id}")
    fun getLocation(
        @PathVariable id: UUID
    ): ResponseEntity<LocationDto> {
        val location = locationService.getLocation(id)

        return if (location != null) {
            ResponseEntity.ok(location)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @Operation(summary = "создание нового места доставки")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @SecurityRequirement(name = "default")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createLocation(
        @RequestBody locationDto: LocationPostDto
    ): ResponseEntity<LocationDto> {
        val location = locationService.createLocation(locationDto)
        val uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path(location.id)
            .build()
            .toUri()
        return ResponseEntity.created(uri).body(location)
    }

    @Operation(summary = "изменение места доставки")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @SecurityRequirement(name = "default")
    @PutMapping("{id}")
    fun updateLocation(
        @PathVariable id: UUID,
        @RequestBody locationDto: LocationPutDto
    ): LocationDto {
        return locationService.updateLocation(id, locationDto)
    }

    @Operation(summary = "удаление места доставки",
        description = "не удаляет из базы, только лишь скрывает из всех GET запросов")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @SecurityRequirement(name = "default")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    fun deleteLocation(
        @PathVariable id: UUID
    ) {
        locationService.deleteLocation(id)
    }
}
