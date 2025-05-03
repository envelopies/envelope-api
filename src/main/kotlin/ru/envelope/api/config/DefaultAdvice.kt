package ru.envelope.api.config

import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import ru.envelope.api.dto.ResponseDto
import ru.envelope.api.exceptions.ApplicationException
import ru.envelope.api.exceptions.IllegalAccessException

@ControllerAdvice
class DefaultAdvice {
    @Operation(description = "произошла ошибка при парсинге запроса")
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun handleMethodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException): ResponseDto {
        return ResponseDto("поле ${ex.name} имеет неверный формат")
    }

    @Operation(description = "произошла ошибка при обработке запроса")
    @ExceptionHandler(ApplicationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun handleApplicationException(ex: ApplicationException): ResponseDto {
        return ResponseDto(ex.message ?: "Неизвестная ошибка")
    }

    @Operation(description = "произошла ошибка при доступе к объекту")
    @ExceptionHandler(IllegalAccessException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun handleIllegalAccessException(ex: IllegalAccessException): ResponseDto {
        return ResponseDto(ex.message ?: "Неизвестная ошибка")
    }
}
