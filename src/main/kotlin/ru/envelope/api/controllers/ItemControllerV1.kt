package ru.envelope.api.controllers

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import ru.envelope.api.dto.item.ItemDto
import ru.envelope.api.dto.item.ItemPostDto
import ru.envelope.api.entities.User
import ru.envelope.api.services.ItemService

@Tag(name = "items (v1)")
@SecurityRequirement(name = "default")
@RestController
@RequestMapping(path = ["v1/items"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ItemControllerV1(
    private val itemService: ItemService
) {
    @PreAuthorize("permitAll()")
    @GetMapping
    fun getItems(
        @RequestParam("pageNumber", required = false, defaultValue = "0") pageNumber: Int,
        @RequestParam("pageSize", required = false, defaultValue = "10") pageSize: Int,
        @RequestParam("sortField", required = false, defaultValue = "createdAt") sortField: String,
        @RequestParam("sortDirection", required = false, defaultValue = "DESC") sortDirection: Sort.Direction,
    ): List<ItemDto> {
        return itemService.getItems(pageNumber, pageSize, sortField, sortDirection)
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createItem(
        @RequestBody itemDto: ItemPostDto,
        @AuthenticationPrincipal user: User
    ): ItemDto {
        return itemService.createItem(itemDto, user)
    }
}
