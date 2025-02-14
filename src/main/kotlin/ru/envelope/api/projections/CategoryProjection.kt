package ru.envelope.api.projections

import java.util.UUID

interface CategoryProjection {
    fun getId(): UUID
    fun getTitle(): String
    fun getIconUrl(): String?
    fun getParentCategoryId(): UUID?
}
