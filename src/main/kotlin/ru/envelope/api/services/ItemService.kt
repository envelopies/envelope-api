package ru.envelope.api.services

import org.springframework.data.domain.Sort
import ru.envelope.api.dto.ItemDto

interface ItemService {
    fun getItems(
        pageNumber: Int,
        pageSize: Int,
        sortField: String,
        sortOrder: Sort.Direction
    ): List<ItemDto>
}
