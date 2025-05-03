package ru.envelope.api.services

import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.envelope.api.config.PictureConfig
import ru.envelope.api.entities.Picture
import ru.envelope.api.entities.User
import ru.envelope.api.exceptions.IllegalAccessToPictureException
import ru.envelope.api.exceptions.NotPictureFileException
import ru.envelope.api.repositories.ItemRepository
import ru.envelope.api.repositories.PictureRepository
import java.io.File
import java.nio.file.Path
import java.util.*

@Service
class PictureServiceV1(
    private val pictureConfig: PictureConfig,
    private val pictureRepository: PictureRepository,
    private val itemRepository: ItemRepository,
    private val webPConverterService: WebPConverterService
) : PictureService {
    companion object {
        val enabledContentTypes = setOf(MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/webp")
    }

    override fun createPicture(blob: MultipartFile): UUID {
        if (!enabledContentTypes.contains(blob.contentType)) {
            throw NotPictureFileException()
        }

        val pictureEntity = pictureRepository.save(Picture())

        if (blob.contentType != "image/webp") {
            val tempFile = File.createTempFile("nvk", null)
            blob.transferTo(tempFile)
            webPConverterService.convertAndSave(tempFile, getPathToWebP(pictureEntity.id))
        } else {
            blob.transferTo(getPathToWebP(pictureEntity.id))
        }

        return pictureEntity.id
    }

    override fun deletePicture(id: UUID, user: User) {
        val itemsWithPicture = itemRepository.getItemsByPictureId(id)

        if (itemsWithPicture.isEmpty()
            || itemsWithPicture.all { it.createdBy.id == user.id }
            || user.authorities.any { it == "ROLE_ADMIN" })
        {
            try {
                val isOk = getPathToWebP(id).toFile().delete()
                if (!isOk) throw Exception()
                pictureRepository.deleteById(id)
            } catch (e: Exception) {
                throw Exception()
            }
        }

        if (!user.authorities.any { it == "ROLE_ADMIN" }) {
            throw IllegalAccessToPictureException(itemsWithPicture.filter { it.createdBy.id != user.id }.map { it.id })
        }
    }

    private fun getPathToWebP(id: UUID): Path = Path.of(pictureConfig.baseDir, "${id}.webp")
}
