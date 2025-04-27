package ru.envelope.api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.envelope.api.services.PictureService
import java.util.*

@Tag(name = "pictures (v1)", description = "работа с картинками")
@RestController
@RequestMapping(value = ["v1/pictures"], produces = [MediaType.APPLICATION_JSON_VALUE])
class PictureControllerV1(
    private val pictureService: PictureService
) {
    @Operation(summary = "создание файла")
    @PostMapping("")
    fun createPicture(@RequestPart("file") file: MultipartFile): UUID {
        return pictureService.createPicture(file)
    }
}