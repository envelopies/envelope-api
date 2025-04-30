package ru.envelope.api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.envelope.api.entities.User
import ru.envelope.api.services.PictureService
import java.util.*

@Tag(name = "pictures (v1)", description = "работа с картинками")
@RestController
@RequestMapping(value = ["v1/pictures"], produces = [MediaType.APPLICATION_JSON_VALUE])
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@SecurityRequirement(name = "default")
class PictureControllerV1(
    private val pictureService: PictureService
) {
    @Operation(summary = "создание файла")
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPicture(@RequestPart("file") file: MultipartFile): UUID {
        return pictureService.createPicture(file)
    }

    @Operation(summary = "удаление файла", description = "доступно только тем, кто создал товар с этой картинкой, или админу")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePicture(@PathVariable id: UUID, @AuthenticationPrincipal user: User) {
        pictureService.deletePicture(id, user)
    }
}
