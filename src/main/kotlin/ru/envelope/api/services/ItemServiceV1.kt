package ru.envelope.api.services

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.envelope.api.dto.ItemDto
import ru.envelope.api.entities.Item
import ru.envelope.api.mappers.ItemMapper
import ru.envelope.api.repositories.ItemRepository

@Service
class ItemServiceV1(
    private val itemRepository: ItemRepository
) : ItemService {
    override fun getItems(pageNumber: Int, pageSize: Int, sortField: String, sortOrder: Sort.Direction): List<ItemDto> {
        return itemRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortOrder, sortField))).map(ItemMapper).toList()
    }
}
