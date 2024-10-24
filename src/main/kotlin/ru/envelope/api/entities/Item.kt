package ru.envelope.api.entities

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "items")
class Item(
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var price: Int,

    @Column(nullable = false)
    var createdAt: Instant,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    var createdBy: User,
)
