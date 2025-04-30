package ru.envelope.api.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "pictures")
class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID
}
