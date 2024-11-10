package ru.envelope.api.mappers

import ru.envelope.api.dto.category.CategoryDto
import ru.envelope.api.projections.CategoryProjection
import java.util.function.Function

object CategoryProjectionMapper: Function<CategoryProjection, CategoryDto> {
    override fun apply(projection: CategoryProjection): CategoryDto = CategoryDto(
        id = projection.getId().toString(),
        title = projection.getTitle(),
        parentCategoryId = projection.getParentCategoryId()?.toString(),
    )
}
