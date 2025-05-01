package ru.envelope.api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import ru.envelope.api.dto.user.UserDto
import ru.envelope.api.dto.user.UserPutDto
import ru.envelope.api.services.UserService

@Tag(name = "users (v1)", description = "управление пользователями")
@RestController
@RequestMapping(value = ["v1/users"], produces = [MediaType.APPLICATION_JSON_VALUE])
@SecurityRequirement(name = "default")
@Secured("hasAnyRole('ADMIN')")
class UsersControllerV1(
    private val userService: UserService
) {
    @Operation(summary = "получение списка всех пользователей")
    @GetMapping
    fun getUsers(
        @RequestParam("pageNumber", required = false, defaultValue = "0") pageNumber: Int,
        @RequestParam("pageSize", required = false, defaultValue = "10") pageSize: Int,
        @RequestParam("sortField", required = false, defaultValue = "id") sortField: String,
        @RequestParam("sortDirection", required = false, defaultValue = "DESC") sortDirection: Sort.Direction,
    ): List<UserDto> {
        return userService.getUsers(pageNumber, pageSize, sortField, sortDirection)
    }

    @Operation(summary = "изменение пользователя")
    @PutMapping("{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody userDto: UserPutDto
    ): UserDto {
        return userService.updateUser(id, userDto)
    }
}
