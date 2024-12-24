package ru.envelope.api.services

import org.springframework.data.domain.Sort
import ru.envelope.api.dto.user.UserDto
import ru.envelope.api.entities.User

interface UserService {
    /**
     * Поиск пользователя по ID из телеграмм.
     */
    fun findById(id: Long): User?

    /**
     * Поиск всех пользователей.
     */
    fun getUsers(
        pageNumber: Int,
        pageSize: Int,
        sortField: String,
        sortOrder: Sort.Direction
    ): List<UserDto>

    fun createUser(
        id: Long,
        firstName: String,
        lastName: String? = null,
        username: String? = null
    ): User
}
