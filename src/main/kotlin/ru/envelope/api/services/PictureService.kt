package ru.envelope.api.services

import org.springframework.web.multipart.MultipartFile
import ru.envelope.api.entities.User
import java.util.UUID

interface PictureService {
    fun createPicture(blob: MultipartFile): UUID
    fun deletePicture(id: UUID, user: User)
}