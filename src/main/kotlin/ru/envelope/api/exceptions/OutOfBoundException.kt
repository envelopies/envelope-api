package ru.envelope.api.exceptions

class OutOfBoundException(
    private val propertyName: String,
    private val badValue: Any,
    private val constraint: String
) : ApplicationException("Поле $propertyName имеет некорректное значение $badValue (требуется $constraint)")
