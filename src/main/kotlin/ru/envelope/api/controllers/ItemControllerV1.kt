package ru.envelope.api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
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
import ru.envelope.api.dto.item.ItemPutDto
import ru.envelope.api.entities.User
import ru.envelope.api.services.ItemService
import java.util.*

@Tag(name = "items (v1)", description = "работа с товарами")
@RestController
@RequestMapping(path = ["v1/items"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ItemControllerV1(
    private val itemService: ItemService
) {
    @Operation(summary = "получение всего списка товаров")
    @GetMapping
    fun getItems(
        @RequestParam("pageNumber", required = false, defaultValue = "0") pageNumber: Int,
        @RequestParam("pageSize", required = false, defaultValue = "10") pageSize: Int,
        @RequestParam("sortField", required = false, defaultValue = "createdAt") sortField: String,
        @RequestParam("sortDirection", required = false, defaultValue = "DESC") sortDirection: Sort.Direction,
        @RequestParam("price", required = false)
        @Parameter(example = "10000:100000", description = """
            указание минимальной и максимальной цены товаров в формате `<min>:<max>`
            первое число это минимальная граница цены, второе - максимальная. оба могут отсутствовать
            может быть в виде `10000:` то есть от 100 рублей,
            `:100000` то есть до 1000 рублей,
            `10000:100000` то есть от 100 до 1000 рублей
            или `null` то есть любые цены
            """)
        priceFilter: String?,
    ): List<ItemDto> {
        return itemService.getItems(pageNumber, pageSize, sortField, sortDirection, priceFilter)
    }

    @Operation(summary = "получение случайных товаров", description = "выдаёт 8 случайных неудалённых товаров из базы")
    @GetMapping("random")
    fun getRandomItems(): List<ItemDto> {
        return itemService.getRandomItems()
    }

    @Operation(summary = "получение информации о конкретном товаре")
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

    @Operation(summary = "создание нового товара", description = "доступно только пользователю, нельзя админу")
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

    @Operation(summary = "изменение товара", description = "изменение товара, в том числе подтверждение")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SecurityRequirement(name = "default")
    @PutMapping("{id}")
    fun updateItem(
        @PathVariable("id") id: UUID,
        @RequestBody itemDto: ItemPutDto,
        @AuthenticationPrincipal user: User,
    ): ItemDto {
        return itemService.updateItem(id, itemDto, user)
    }

    @Operation(summary = "удаление товара")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SecurityRequirement(name = "default")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    fun deleteItem(
        @PathVariable("id") id: UUID,
    ) {
        itemService.deleteItem(id)
    }

}
