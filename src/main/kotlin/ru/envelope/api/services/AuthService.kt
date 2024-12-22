package ru.envelope.api.services

import ru.envelope.api.dto.AuthRequestDto

interface AuthService {
    fun register(telegramData: AuthRequestDto)
}