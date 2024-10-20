package ru.envelope.api.services

import ru.envelope.api.entities.User

interface UserService {
    /**
     * Поиск пользователя по ID из телеграмм.
     */
    fun findById(id: Long): User?
}
