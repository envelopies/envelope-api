package ru.envelope.api.config

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import ru.envelope.api.dto.ErrorResponseDto

@ControllerAdvice
class DefaultAdvice {
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun handleMethodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException): ErrorResponseDto {
        return ErrorResponseDto("поле ${ex.name} имеет неверный формат")
    }
}
