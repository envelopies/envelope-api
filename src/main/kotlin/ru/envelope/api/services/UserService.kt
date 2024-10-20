package ru.envelope.api.services

import ru.envelope.api.entities.User

interface UserService {
    fun findById(id: Long): User?
}
