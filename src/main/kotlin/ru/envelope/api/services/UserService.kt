package ru.envelope.api.services

import org.springframework.data.domain.Sort
import ru.envelope.api.dto.user.SellerDto
import ru.envelope.api.dto.user.UserDto
import ru.envelope.api.dto.user.UserPutDto
import ru.envelope.api.entities.User

interface UserService {
    /**
     * Поиск пользователя по ID из телеграмм.
     */
    fun getUser(id: Long): User?

    /**
     * Поиск всех пользователей.
     */
    fun getUsers(
        pageNumber: Int,
        pageSize: Int,
        sortField: String,
        sortOrder: Sort.Direction
    ): List<UserDto>

    /**
     * Передаёт всех избранных продавцов.
     */
    fun getAllVerified(): List<SellerDto>

    fun createUser(
        id: Long,
        firstName: String,
        lastName: String? = null,
        username: String? = null
    ): User

    fun updateUser(
        id: Long,
        userDto: UserPutDto
    ): UserDto
}
