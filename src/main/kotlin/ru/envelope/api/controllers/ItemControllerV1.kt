package ru.envelope.api.controllers

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import ru.envelope.api.dto.item.ItemDto
import ru.envelope.api.dto.item.ItemPostDto
import ru.envelope.api.entities.User
import ru.envelope.api.services.ItemService
import java.util.*

@Tag(name = "items (v1)")
@RestController
@RequestMapping(path = ["v1/items"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ItemControllerV1(
    private val itemService: ItemService
) {
    @GetMapping
    fun getItems(
        @RequestParam("pageNumber", required = false, defaultValue = "0") pageNumber: Int,
        @RequestParam("pageSize", required = false, defaultValue = "10") pageSize: Int,
        @RequestParam("sortField", required = false, defaultValue = "createdAt") sortField: String,
        @RequestParam("sortDirection", required = false, defaultValue = "DESC") sortDirection: Sort.Direction,
    ): List<ItemDto> {
        return itemService.getItems(pageNumber, pageSize, sortField, sortDirection)
    }

    @GetMapping("{id}")
    fun getItem(
        @PathVariable("id") id: UUID,
    ): ResponseEntity<ItemDto> {
        val item = itemService.getItem(id)

        return if (item != null) {
            ResponseEntity.ok(item)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @SecurityRequirement(name = "default")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createItem(
        @RequestBody itemDto: ItemPostDto,
        @AuthenticationPrincipal user: User,
    ): ItemDto {
        return itemService.createItem(itemDto, user)
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SecurityRequirement(name = "default")
    @PutMapping
    fun updateItem(
        @RequestBody itemDto: ItemPostDto,
        @AuthenticationPrincipal user: User,
    ): String {
        return "Ok! 123"
    }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SecurityRequirement(name = "default")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    fun deleteItem(
        @PathVariable("id") id: UUID,
    ) {
        return
    }

}
