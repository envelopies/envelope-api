package ru.envelope.api.repositories

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.envelope.api.entities.Location
import java.util.*

@Repository
interface LocationRepository : JpaRepository<Location, UUID> {
    @Query("""
        from Location l
        where l.removed = false
    """)
    override fun findAll(pageable: Pageable): Page<Location>
}
