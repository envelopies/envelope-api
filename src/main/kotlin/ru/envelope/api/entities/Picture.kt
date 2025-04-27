package ru.envelope.api.entities

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.util.*

@Entity
@Table(name = "pictures")
class Picture(
    @Column(name = "title", nullable = false)
    @NotNull
    val title: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID
}
