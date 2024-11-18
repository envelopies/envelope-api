package ru.envelope.api.services

import org.springframework.data.domain.Sort
import ru.envelope.api.dto.category.CategoryDto
import ru.envelope.api.dto.category.CategoryPostDto
import ru.envelope.api.dto.category.CategoryPutDto
import ru.envelope.api.dto.category.CategoryTreeNodeDto
import java.util.UUID

interface CategoryService {
    fun getCategories(
        pageNumber: Int,
        pageSize: Int,
        sortField: String,
        sortOrder: Sort.Direction
    ): List<CategoryDto>

    fun getCategoriesTree(): List<CategoryTreeNodeDto>

    fun getCategory(id: UUID): CategoryDto?

    fun createCategory(
        categoryDto: CategoryPostDto
    ): CategoryDto

    fun updateCategory(
        id: UUID,
        categoryDto: CategoryPutDto
    ): CategoryDto

    fun deleteCategory(id: UUID)
}
