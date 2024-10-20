package ru.envelope.api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
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

@Tag(name = "auth (v1)")
@RestController
@RequestMapping(path = ["v1/auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthControllerV1(
    private val telegramService: TelegramService,
    private val jwtTokenService: JwtTokenService,
    private val userService: UserService
) {
    @Operation(summary = "логин пользователя из телеграмма в данном сервиса", description = "сначала требуется зайти в телеграмм, который передаст вам данные, требуемые для Body этого запроса; если значение `hash` не совпадёт с расчётным (рассчитывает по данным этого Body и секретному ключу бота телеграмм)")
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
