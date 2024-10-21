package ru.envelope.api.entities

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.ZonedDateTime
import java.util.*

@Entity
@Table(name = "items")
data class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    @NotNull
    @NotBlank
    val title: String,

    @NotNull
    val description: String,

    @NotNull
    val price: Int,

    @NotNull
    val createdAt: ZonedDateTime,

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    val user: User,
)
