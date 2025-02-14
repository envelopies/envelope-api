package ru.envelope.api.dto.category

import io.swagger.v3.oas.annotations.media.Schema
import ru.envelope.api.projections.CategoryProjection

data class CategoryTreeNodeDto(
    @Schema(description = "ID категории товара")
    val id: String,

    @Schema(description = "название категории товара")
    val title: String,

    @Schema(description = "иконка категории")
    val iconUrl: String?
) {
    @Schema(description = "дочерние категории")
    var children: List<CategoryTreeNodeDto> = ArrayList()

    companion object {
        fun fromProjection(projection: CategoryProjection): CategoryTreeNodeDto {
            return CategoryTreeNodeDto(
                id = projection.getId().toString(),
                title = projection.getTitle(),
                iconUrl = projection.getIconUrl()
            )
        }
    }
}
