package ru.envelope.api.entities

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SourceType
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "items")
data class Item(
    @field:Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    @NotBlank
    val title: String,

    val description: String,

    val price: Int,

    @field:Column(updatable = false)
    @field:CreationTimestamp(source = SourceType.DB)
    var createdAt: Instant,

    @field:UpdateTimestamp(source = SourceType.DB)
    var updatedAt: Instant,

    @field:ManyToOne(fetch = FetchType.EAGER)
    var createdBy: User,

    @field:ManyToOne(fetch = FetchType.EAGER)
    var updatedBy: User
)
