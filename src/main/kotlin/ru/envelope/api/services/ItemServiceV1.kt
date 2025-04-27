package ru.envelope.api.services

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.envelope.api.dto.item.ItemDto
import ru.envelope.api.dto.item.ItemPostDto
import ru.envelope.api.dto.item.ItemPutDto
import ru.envelope.api.entities.Item
import ru.envelope.api.entities.User
import ru.envelope.api.exceptions.CategoryNotFoundException
import ru.envelope.api.exceptions.ItemNotFoundException
import ru.envelope.api.mappers.ItemProjectionMapper
import ru.envelope.api.projections.ItemProjection
import ru.envelope.api.repositories.CategoryRepository
import ru.envelope.api.repositories.ItemRepository
import ru.envelope.api.repositories.LocationRepository
import java.util.*

@Service
class ItemServiceV1(
    private val itemRepository: ItemRepository,
    private val categoryRepository: CategoryRepository,
    private val locationRepository: LocationRepository
) : ItemService {
    override fun getItems(pageNumber: Int, pageSize: Int, sortField: String, sortOrder: Sort.Direction): List<ItemDto> {
        val pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortOrder, sortField))
        return itemRepository.findAllWithProjection(pageRequest)
            .groupBy(ItemProjection::getId)
            .values
            .map(ItemProjectionMapper::apply)
            .toList()
    }

    override fun getItem(id: UUID): ItemDto? {
        return itemRepository.findByIdWithProjection(id).let(ItemProjectionMapper::apply)
    }

    override fun createItem(itemDto: ItemPostDto, user: User): ItemDto {
        val category = categoryRepository.findById(itemDto.categoryId)

        if (category.isEmpty) {
            throw CategoryNotFoundException(itemDto.categoryId)
        }

        var item = Item(
            title = itemDto.title,
            description = itemDto.description,
            price = itemDto.price,
            category = category.get()
        )

        if (itemDto.deliveryAddresses?.isEmpty() == false) {
            item.deliveryAddresses = itemDto.deliveryAddresses
                .map { locationRepository.findById(it) }
                .filter { it.isPresent }
                .map { it.get() }
                .toSet()
        }

        item = itemRepository.save(item)

        return getItem(item.id)!!
    }

    override fun updateItem(id: UUID, itemDto: ItemPutDto, user: User): ItemDto {
        val itemEntity = itemRepository.findById(id)

        if (itemEntity.isEmpty) {
            throw ItemNotFoundException(id)
        }

        val item = itemEntity.get()
        if (itemDto.title != null) {
            item.title = itemDto.title
        }
        if (itemDto.description != null) {
            item.description = itemDto.description
        }
        if (itemDto.price != null) {
            item.price = itemDto.price
        }
        if (itemDto.deliveryAddresses?.isEmpty() == false) {
            item.deliveryAddresses = itemDto.deliveryAddresses
                .map { locationRepository.findById(it) }
                .filter { it.isPresent }
                .map { it.get() }
                .toSet()
        }
        // только админ может разрешать показывать товар
        if (itemDto.published != null && user.authorities.any { it == "ROLE_ADMIN" }) {
            item.published = itemDto.published
        }
        // только админ может восстановить товар
        if (itemDto.removed != null && user.authorities.any { it == "ROLE_ADMIN" }) {
            item.removed = itemDto.removed
        }
        itemRepository.save(item)

        return getItem(item.id)!!
    }

    override fun deleteItem(id: UUID) {
        val itemEntity = itemRepository.findById(id)

        if (itemEntity.isPresent) {
            val item = itemEntity.get()
            item.removed = true
            itemRepository.save(item)
        }
    }
}
