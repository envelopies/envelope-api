package ru.envelope.api.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "locations")
class Location(
    @Column(name = "title", nullable = false)
    var title: String,

    /** Широта */
    @Column(name = "latitude", nullable = true)
    var latitude: BigDecimal?,

    /** Долгота */
    @Column(name = "longitude", nullable = true)
    var longitude: BigDecimal?,

    @Column(name = "removed", nullable = false)
    var removed: Boolean = false
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID
}
