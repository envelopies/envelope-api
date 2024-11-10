package ru.envelope.api.exceptions

import java.util.UUID

class ItemNotFoundException(
    private val id: UUID
) : ApplicationException("Товар не найден: $id")
