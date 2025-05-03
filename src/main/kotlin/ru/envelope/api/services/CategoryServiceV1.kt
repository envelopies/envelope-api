package ru.envelope.api.services

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.envelope.api.dto.category.CategoryDto
import ru.envelope.api.dto.category.CategoryPostDto
import ru.envelope.api.dto.category.CategoryPutDto
import ru.envelope.api.dto.category.CategoryTreeNodeDto
import ru.envelope.api.entities.Category
import ru.envelope.api.exceptions.BadSortFieldException
import ru.envelope.api.exceptions.CategoryNotFoundException
import ru.envelope.api.mappers.CategoryProjectionMapper
import ru.envelope.api.projections.CategoryProjection
import ru.envelope.api.repositories.CategoryRepository
import ru.envelope.api.repositories.ItemRepository
import ru.envelope.api.services.LocationServiceV1.Companion
import java.util.*

@Service
class CategoryServiceV1(
    private val categoryRepository: CategoryRepository,
    private val itemRepository: ItemRepository
): CategoryService {
    companion object {
        val allowedSortFields = setOf("title")
    }

    override fun getCategories(pageNumber: Int, pageSize: Int, sortField: String, sortOrder: Sort.Direction): List<CategoryDto> {
        val pageRequest = getPageRequest(pageNumber, pageSize, sortField, sortOrder)
        return categoryRepository.findAllWithProjection(pageRequest)
            .map(CategoryProjectionMapper)
            .toList()
    }

    override fun getCategoriesTree(): List<CategoryTreeNodeDto> {
        val sortRequest = Pageable.unpaged(Sort.by(Sort.Direction.ASC, "parentCategoryId"))
        val categoryProjections = categoryRepository.findAllWithProjection(sortRequest)
        return categoryProjections.filter {
            it.getParentCategoryId() == null
        }.map {
            fillChildren(it, categoryProjections)
        }.toList()
    }

    private fun fillChildren(parent: CategoryProjection, allItems: Iterable<CategoryProjection>): CategoryTreeNodeDto {
        val nodeDto = CategoryTreeNodeDto.fromProjection(parent)
        nodeDto.children = allItems.filter {
            it.getParentCategoryId() == parent.getId()
        }.map {
            fillChildren(it, allItems)
        }.toList()
        return nodeDto
    }

    override fun getCategory(id: UUID): CategoryDto? {
        return categoryRepository.findByIdWithProjection(id)?.let(CategoryProjectionMapper::apply)
    }

    override fun createCategory(categoryDto: CategoryPostDto): CategoryDto {
        var parentCategory: Category? = null

        if (categoryDto.parentCategoryId != null) {
            val parentCategoryEntity = categoryRepository.findById(categoryDto.parentCategoryId)
            if (parentCategoryEntity.isEmpty) {
                throw CategoryNotFoundException(categoryDto.parentCategoryId)
            }

            parentCategory = parentCategoryEntity.get()
        }

        val category = categoryRepository.save(Category(
            title = categoryDto.title,
            iconUrl = categoryDto.iconUrl,
            parentCategory = parentCategory
        ))

        return getCategory(category.id)!!
    }

    override fun updateCategory(id: UUID, categoryDto: CategoryPutDto): CategoryDto {
        val categoryEntity = categoryRepository.findById(id)

        if (categoryEntity.isEmpty) {
            throw CategoryNotFoundException(id)
        }

        val category = categoryEntity.get()
        if (categoryDto.title != null) {
            category.title = categoryDto.title
        }
        if (categoryDto.parentCategoryId != null) {
            val parentCategoryEntity = categoryRepository.findById(categoryDto.parentCategoryId)
            if (parentCategoryEntity.isEmpty) {
                throw CategoryNotFoundException(categoryDto.parentCategoryId)
            }
            category.parentCategory = parentCategoryEntity.get()
        }
        categoryRepository.save(category)

        return getCategory(category.id)!!
    }

    override fun deleteCategory(id: UUID) {
        val categoryEntity = categoryRepository.findById(id)

        if (categoryEntity.isPresent) {
            val category = categoryEntity.get()
            category.removed = true
            categoryRepository.save(category)

            itemRepository.setRemovedOnCategoryItems(id)
        }
    }

    private fun getPageRequest(pageNumber: Int, pageSize: Int, sortField: String, sortOrder: Sort.Direction): PageRequest {
        if (!allowedSortFields.contains(sortField)) {
            throw BadSortFieldException(allowedSortFields)
        }
        return PageRequest.of(pageNumber, pageSize, Sort.by(sortOrder, sortField))
    }
}
