package ru.envelope.api.exceptions

class UserNotFoundException(id: Long) : ApplicationException("Пользователь не найден: ${id}")
