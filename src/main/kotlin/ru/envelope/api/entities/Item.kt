package ru.envelope.api.entities

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "items")
class Item(
    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var price: Int,

    @Column(nullable = false)
    var createdAt: Instant,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var user: User,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID
}
