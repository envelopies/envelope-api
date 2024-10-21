package ru.envelope.api.entities

import jakarta.persistence.*
import java.security.Principal

@Entity
@Table(name = "users")
data class User(
    /**
     * ID пользователя в телеграмме.
     */
    @Id
    val id: Long,

    /**
     * Имя пользователя для отображения в карточке.
     */
    val username: String?,

    /**
     * Будут верифицированные пользователи.
     * Возможно с галочкой?
     * Признак пока что ни на что не влияет.
     */
    val verified: Boolean,

    /**
     * Список ролей пользователя.
     */
    @ElementCollection(fetch = FetchType.EAGER, targetClass = String::class)
    @CollectionTable(name = "authorities", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "authority", nullable = false)
    val authorities: Set<String> = HashSet(),

    /**
     * Список товаров пользователя.
     */
    @OneToMany(fetch = FetchType.LAZY)
    val items: Set<Item>,
): Principal {
    override fun getName(): String = id.toString()
}
