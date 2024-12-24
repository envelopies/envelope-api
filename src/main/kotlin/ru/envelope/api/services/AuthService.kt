package ru.envelope.api.services

import ru.envelope.api.dto.AuthRequestDto
import ru.envelope.api.dto.AuthResponseDto

interface AuthService {
    fun tryLogin(telegramAuthData: AuthRequestDto): AuthResponseDto
}