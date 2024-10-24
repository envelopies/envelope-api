package ru.envelope.api.entities

import jakarta.persistence.*
import java.security.Principal

@Entity
@Table(name = "users")
class User(
    /**
     * ID пользователя в телеграмме.
     */
    @Id
    @Column(nullable = false)
    var id: Long?,

    /**
     * Имя пользователя для отображения в карточке.
     */
    @Column(nullable = false)
    var username: String?,

    /**
     * Будут верифицированные пользователи.
     * Возможно с галочкой?
     * Признак пока что ни на что не влияет.
     */
    @Column(nullable = false)
    var verified: Boolean?,

    /**
     * Список ролей пользователя.
     */
    @ElementCollection(fetch = FetchType.EAGER, targetClass = String::class)
    @CollectionTable(name = "authorities", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "authority")
    var authorities: MutableSet<String> = mutableSetOf(),

    /**
     * Список товаров пользователя.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    var items: MutableSet<Item> = mutableSetOf(),
): Principal {
    override fun getName(): String = id.toString()
}
