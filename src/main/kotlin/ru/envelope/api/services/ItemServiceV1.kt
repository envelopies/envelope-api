package ru.envelope.api.services

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.envelope.api.dto.item.ItemDto
import ru.envelope.api.dto.item.ItemPostDto
import ru.envelope.api.entities.Item
import ru.envelope.api.entities.User
import ru.envelope.api.mappers.ItemMapper
import ru.envelope.api.mappers.ItemProjectionMapper
import ru.envelope.api.repositories.ItemRepository
import java.time.ZonedDateTime

@Service
class ItemServiceV1(
    private val itemRepository: ItemRepository
) : ItemService {
    override fun getItems(pageNumber: Int, pageSize: Int, sortField: String, sortOrder: Sort.Direction): List<ItemDto> {
        val pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortOrder, sortField))
        return itemRepository.findAllWithProjection(pageRequest)
            .map(ItemProjectionMapper)
            .toList()
    }

    override fun createItem(itemDto: ItemPostDto, user: User): ItemDto {
        val item = itemRepository.save(Item(
            id = null,
            title = itemDto.title,
            description = itemDto.description,
            price = itemDto.price,
            createdAt = ZonedDateTime.now(),
            user = user
        ))

        return ItemMapper.apply(item)
    }
}
