package ru.envelope.api.dto.category

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class CategoryPutDto(
    @Schema(description = "название категории")
    val title: String?,

    @Schema(description = "иконка категории")
    val iconUrl: String?,

    @Schema(description = "удалена ли категория")
    val removed: Boolean?,

    @Schema(description = "ID родительской категории")
    val parentCategoryId: UUID?,
)
