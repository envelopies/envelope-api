package ru.envelope.api.services

import org.springframework.data.domain.Sort
import ru.envelope.api.dto.item.ItemDto
import ru.envelope.api.dto.item.ItemPostDto
import ru.envelope.api.entities.User

interface ItemService {
    fun getItems(
        pageNumber: Int,
        pageSize: Int,
        sortField: String,
        sortOrder: Sort.Direction
    ): List<ItemDto>

    fun createItem(
        itemDto: ItemPostDto,
        user: User
    ): ItemDto
}
