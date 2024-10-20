package ru.envelope.api.services

import org.springframework.stereotype.Service
import ru.envelope.api.entities.User
import ru.envelope.api.repositories.UserRepository

@Service
class UserServiceV1(
    private val userRepository: UserRepository
): UserService {
    override fun findById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }
}
