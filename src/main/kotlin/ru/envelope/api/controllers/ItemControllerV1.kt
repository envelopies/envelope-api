package ru.envelope.api.controllers

import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.envelope.api.dto.ItemDto
import ru.envelope.api.services.ItemService

@RestController
@RequestMapping("v1/items")
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
}