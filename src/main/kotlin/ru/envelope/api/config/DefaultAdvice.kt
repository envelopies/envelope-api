package ru.envelope.api.config

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import ru.envelope.api.dto.ErrorResponseDto
import ru.envelope.api.exceptions.ApplicationException

@ControllerAdvice
class DefaultAdvice {
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun handleMethodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException): ErrorResponseDto {
        return ErrorResponseDto("поле ${ex.name} имеет неверный формат")
    }

    @Hidden
    @ExceptionHandler(ApplicationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun handleApplicationException(ex: ApplicationException): ErrorResponseDto {
        return ErrorResponseDto(ex.message ?: "Неизвестная ошибка")
    }
}
