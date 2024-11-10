package ru.envelope.api.dto.category

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class CategoryPostDto(
    @Schema(description = "название категории")
    val title: String,

    @Schema(description = "ID родительской категории")
    val parentCategoryId: UUID?
)
