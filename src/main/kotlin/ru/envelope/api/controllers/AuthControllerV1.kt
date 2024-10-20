package ru.envelope.api.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.envelope.api.dto.AuthRequestDto
import ru.envelope.api.dto.AuthResponseDto
import ru.envelope.api.services.JwtTokenService
import ru.envelope.api.services.TelegramService
import ru.envelope.api.services.UserService

@RestController
@RequestMapping("v1/auth")
class AuthControllerV1(
    private val telegramService: TelegramService,
    private val jwtTokenService: JwtTokenService,
    private val userService: UserService
) {
    @PostMapping
    fun getToken(@RequestBody token: AuthRequestDto): ResponseEntity<AuthResponseDto?> {
        val isOk = telegramService.checkTelegramAuthData(token)

        if (!isOk) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }

        val user = userService.findById(token.id)

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }

        val jwtToken = jwtTokenService.generateToken(user)
        return ResponseEntity.ok(AuthResponseDto(jwtToken))
    }
}
