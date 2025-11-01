package ru.envelope.api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.envelope.api.dto.user.SellerDto
import ru.envelope.api.services.UserService

@Tag(name = "sellers (v1)", description = "продавцы товаров")
@RequestMapping(value = ["v1/sellers"], produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class SellersControllerV1(
    private val userService: UserService,
) {
    @Operation(summary = "список всех избранных продавцов")
    @GetMapping(value = ["favorites"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllFavoriteSellers(): List<SellerDto> {
        return userService.getAllVerified()
    }
}
