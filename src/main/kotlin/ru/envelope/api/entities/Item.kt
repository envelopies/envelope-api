package ru.envelope.api.entities

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "items")
class Item(
    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var price: Int,

    @Column(nullable = false)
    var quantity: Int,

    @Column(nullable = false)
    var unit: String,

    @Column(nullable = false)
    var published: Boolean = false,

    @Column(nullable = false)
    var removed: Boolean = false,

    @ManyToOne
    @JoinColumn(nullable = false)
    var category: Category,

    @ManyToMany(targetEntity = Location::class)
    var deliveryAddresses: Set<Location> = mutableSetOf(),

    @ManyToMany(targetEntity = Picture::class)
    var pictures: Set<Picture> = mutableSetOf(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @Column(nullable = false, updatable = false)
    @CreatedDate
    lateinit var createdAt: Instant

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    @CreatedBy
    lateinit var createdBy: User
}
