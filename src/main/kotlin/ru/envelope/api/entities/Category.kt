package ru.envelope.api.entities

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "categories")
class Category(
    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var removed: Boolean = false,

    @Column(nullable = true)
    var iconUrl: String?,

    @ManyToOne
    @JoinColumn(nullable = true)
    var parentCategory: Category?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID
}
