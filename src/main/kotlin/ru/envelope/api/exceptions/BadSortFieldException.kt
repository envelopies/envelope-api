package ru.envelope.api.exceptions

class BadSortFieldException(allowedFields: Collection<String>) : ApplicationException("Поле сортировки указано неверно. Разрешённые поля для сортировки: ${allowedFields.joinToString()}")
