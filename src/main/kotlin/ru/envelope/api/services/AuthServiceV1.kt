package ru.envelope.api.services

import org.springframework.stereotype.Service
import ru.envelope.api.dto.AuthRequestDto
import ru.envelope.api.dto.AuthResponseDto
import ru.envelope.api.exceptions.ExpiredTelegramTokenException
import ru.envelope.api.exceptions.IllegalTelegramTokenException
import ru.envelope.api.models.TelegramDataStatus

@Service
class AuthServiceV1(
    private val telegramService: TelegramService,
    private val userService: UserService,
    private val jwtTokenService: JwtTokenService
): AuthService {
    override fun tryLogin(telegramAuthData: AuthRequestDto): AuthResponseDto {
        val status = telegramService.checkTelegramAuthData(telegramAuthData)

        if (status == TelegramDataStatus.EXPIRED) {
            throw ExpiredTelegramTokenException()
        } else if (status == TelegramDataStatus.BAD) {
            throw IllegalTelegramTokenException()
        }

        val user = userService.findById(telegramAuthData.id)

        if (user == null) {
            userService.createUser(telegramAuthData.id, telegramAuthData.firstName, telegramAuthData.lastName, telegramAuthData.username)

            return AuthResponseDto(
                success = true,
                token = null
            )
        }

        return AuthResponseDto(
            success = true,
            token = jwtTokenService.generateToken(user)
        )
    }
}
