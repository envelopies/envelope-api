package ru.envelope.api.services

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface PictureService {
    fun createPicture(blob: MultipartFile): UUID
}