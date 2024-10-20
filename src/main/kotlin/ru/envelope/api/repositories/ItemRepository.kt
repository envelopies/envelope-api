package ru.envelope.api.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.envelope.api.entities.Item
import java.util.*

interface ItemRepository : JpaRepository<Item, UUID>
