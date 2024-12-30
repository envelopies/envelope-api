package ru.envelope.api.exceptions

import java.util.UUID

class LocationNotFoundException(
    id: UUID
) : ApplicationException("Место не найдено: $id")
