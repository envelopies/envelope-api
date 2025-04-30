package ru.envelope.api.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.envelope.api.entities.Picture
import java.util.UUID

interface PictureRepository : JpaRepository<Picture, UUID> {
}