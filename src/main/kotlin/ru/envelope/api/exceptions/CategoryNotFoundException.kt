package ru.envelope.api.exceptions

import java.util.UUID

class CategoryNotFoundException(
    private val id: UUID
) : ApplicationException("Категория товара не найдена: $id")
