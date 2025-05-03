package ru.envelope.api.services

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.envelope.api.dto.item.ItemDto
import ru.envelope.api.dto.item.ItemPostDto
import ru.envelope.api.dto.item.ItemPutDto
import ru.envelope.api.entities.Item
import ru.envelope.api.entities.User
import ru.envelope.api.exceptions.BadSortFieldException
import ru.envelope.api.exceptions.CategoryNotFoundException
import ru.envelope.api.exceptions.IllegalAccessException
import ru.envelope.api.exceptions.ItemNotFoundException
import ru.envelope.api.mappers.ItemProjectionMapper
import ru.envelope.api.projections.ItemProjection
import ru.envelope.api.repositories.CategoryRepository
import ru.envelope.api.repositories.ItemRepository
import ru.envelope.api.repositories.LocationRepository
import ru.envelope.api.repositories.PictureRepository
import ru.envelope.api.util.isAdminOrSpecificId
import java.util.*

private val logger = KotlinLogging.logger {}

@Service
class ItemServiceV1(
    private val itemRepository: ItemRepository,
    private val categoryRepository: CategoryRepository,
    private val locationRepository: LocationRepository,
    private val pictureRepository: PictureRepository,
) : ItemService {
    companion object {
        val allowedSortFields = setOf("title", "price", "published", "createdAt", "category", "deliveryAddresses")
        val sortReplacements = mapOf("category" to "c.title", "deliveryAddresses" to "l.title")
    }

    override fun getItems(pageNumber: Int, pageSize: Int, sortField: String, sortOrder: Sort.Direction): List<ItemDto> {
        val pageRequest = getPageRequest(pageNumber, pageSize, sortField, sortOrder)
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

        if (itemDto.pictures?.isEmpty() == false) {
            item.pictures = itemDto.pictures
                .map { pictureRepository.findById(it) }
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
            logger.warn { "${user.id} пытался обновить товар ${id}, но такого товара не существует" }
            throw ItemNotFoundException(id)
        }

        val item = itemEntity.get()

        if (!isAdminOrSpecificId(user, item.createdBy.id)) {
            logger.warn { "${user.id} пытался получить доступ к ${id}, но не является его владельцем" }
            throw IllegalAccessException()
        }

        if (itemDto.title != null) {
            item.title = itemDto.title
        }
        if (itemDto.description != null) {
            item.description = itemDto.description
        }
        if (itemDto.price != null) {
            item.price = itemDto.price
        }
        if (itemDto.categoryId != null) {
            val category = categoryRepository.findById(itemDto.categoryId)

            if (category.isEmpty) {
                logger.warn { "${user.id} пытался выставить товару $id категорию ${itemDto.categoryId}, но не знал, что её не существует" }
                throw CategoryNotFoundException(itemDto.categoryId)
            }

            item.category = category.get()
        }
        if (itemDto.deliveryAddresses?.isEmpty() == false) {
            item.deliveryAddresses = itemDto.deliveryAddresses
                .map { locationRepository.findById(it) }
                .filter { it.isPresent }
                .map { it.get() }
                .toSet()
        }
        if (itemDto.pictures?.isEmpty() == false) {
            item.pictures = itemDto.pictures
                .map { pictureRepository.findById(it) }
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
        } else {
            logger.warn { "Кто-то пытался удалить товар ${id}, но такого товара не существует" }
        }
    }

    private fun getPageRequest(pageNumber: Int, pageSize: Int, sortField: String, sortOrder: Sort.Direction): PageRequest {
        if (!allowedSortFields.contains(sortField)) {
            logger.warn { "Кто-то пытался использовать $sortField как поле сортировки товара" }
            throw BadSortFieldException(allowedSortFields)
        }
        var sortFieldInEntity = sortField
        if (sortReplacements.containsKey(sortField)) {
            sortFieldInEntity = sortReplacements[sortField]!!
        }
        return PageRequest.of(pageNumber, pageSize, Sort.by(sortOrder, sortFieldInEntity))
    }
}
