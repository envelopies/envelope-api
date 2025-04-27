package ru.envelope.api.services

import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.envelope.api.config.PictureConfig
import ru.envelope.api.entities.Picture
import ru.envelope.api.exceptions.NotPictureFileException
import ru.envelope.api.exceptions.TooBigFileException
import ru.envelope.api.repositories.PictureRepository
import ru.envelope.api.util.toBytesLength
import java.util.*

@Service
class PictureServiceV1(
    private val pictureConfig: PictureConfig,
    private val pictureRepository: PictureRepository
) : PictureService {
    private val enabledContentTypes = setOf(MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/webm", "image/webp")

    override fun createPicture(blob: MultipartFile): UUID {
        if (blob.size > pictureConfig.maxSize.toBytesLength()) {
            throw TooBigFileException(pictureConfig.maxSize)
        }

        if (!enabledContentTypes.contains(blob.contentType)) {
            throw NotPictureFileException()
        }

        val pictureEntity = pictureRepository.save(Picture(blob.name))

        TODO("Not implemented yet")

        return pictureEntity.id
    }
}
