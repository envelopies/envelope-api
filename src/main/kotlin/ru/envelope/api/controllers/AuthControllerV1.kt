package ru.envelope.api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.envelope.api.dto.AuthRequestDto
import ru.envelope.api.dto.AuthResponseDto
import ru.envelope.api.services.AuthService

@Tag(name = "auth (v1)")
@RestController
@RequestMapping(path = ["v1/auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthControllerV1(
    private val authService: AuthService
) {
    @Operation(summary = "логин пользователя из телеграмма в данном сервиса", description = "сначала требуется зайти в телеграмм, который передаст вам данные, требуемые для Body этого запроса; если значение `hash` не совпадёт с расчётным (рассчитывает по данным этого Body и секретному ключу бота телеграмм)")
    @PostMapping
    fun getToken(@RequestBody telegramAuthData: AuthRequestDto): AuthResponseDto {
        return authService.tryLogin(telegramAuthData)
    }
}
