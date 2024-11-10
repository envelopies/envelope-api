package ru.envelope.api.dto.category

import io.swagger.v3.oas.annotations.media.Schema

data class CategoryDto(
    @Schema(description = "ID категории товара")
    val id: String,

    @Schema(description = "название категории товара")
    val title: String,

    @Schema(description = "ID родительской категории")
    val parentCategoryId: String?
)
