package ru.envelope.api.services

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.envelope.api.dto.item.ItemDto
import ru.envelope.api.dto.item.ItemPostDto
import ru.envelope.api.entities.Item
import ru.envelope.api.entities.User
import ru.envelope.api.mappers.ItemProjectionMapper
import ru.envelope.api.repositories.ItemRepository
import ru.envelope.api.util.format
import ru.envelope.api.util.localDateTimeFormatter
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*

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

    override fun getItem(id: UUID): ItemDto? {
        return itemRepository.findByIdWithProjection(id)?.let(ItemProjectionMapper::apply)
    }

    override fun createItem(itemDto: ItemPostDto, user: User): ItemDto {
        val item = itemRepository.save(Item(
            title = itemDto.title,
            description = itemDto.description,
            price = itemDto.price,
            user = user
        ))

        return ItemDto(
            id = item.id.toString(),
            title = item.title,
            description = item.description,
            price = item.price,
            createdAt = item.createdAt.format(),
            username = user.username,
        )
    }
}
