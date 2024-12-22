package ru.envelope.api.services

import org.springframework.stereotype.Service
import ru.envelope.api.dto.AuthRequestDto
import ru.envelope.api.entities.User
import ru.envelope.api.repositories.UserRepository

@Service
class AuthServiceV1(
    private val userRepository: UserRepository
): AuthService {
    override fun register(telegramData: AuthRequestDto) {
        userRepository.save(User(
            id = telegramData.id,
            username = telegramData.firstName,
            verified = false
        ))
    }
}
