package ru.envelope.api.repositories

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import ru.envelope.api.entities.Item
import ru.envelope.api.projections.ItemProjection
import java.util.*

interface ItemRepository : JpaRepository<Item, UUID> {
    @Query(value = """
        SELECT i.id AS id,
               i.title AS title,
               i.description AS description,
               i.price AS price,
               i.quantity AS quantity,
               i.unit AS unit,
               i.created_at AS createdAt,
               u.username AS username,
               c.title AS category,
               l.title AS deliveryAddress,
               i_p.pictures_id AS pictureId
          FROM items i
          JOIN users u ON i.user_id = u.id
          JOIN categories c ON i.category_id = c.id
          LEFT JOIN items_delivery_addresses i_l ON i.id = i_l.item_id
          LEFT JOIN locations l ON i_l.delivery_addresses_id = l.id
          LEFT JOIN items_pictures i_p ON i.id = i_p.item_id
         WHERE i.removed = false
           AND (l.id IS NULL OR l.removed = false)
           AND i.price between :min and :max
    """, countQuery = """
        SELECT COUNT(*)
          FROM items
    """, nativeQuery = true)
    fun findAllWithProjection(min: Int, max: Int, page: Pageable): Page<ItemProjection>

    @Query("""
        select i.id as id,
               i.title as title,
               i.description as description,
               i.price as price,
               i.quantity as quantity,
               i.unit as unit,
               i.createdAt as createdAt,
               u.username as username,
               c.title as category,
               i_l.title as deliveryAddress,
               p.id as pictureId
          from Item i
          join i.createdBy u
          join i.category c
          left join i.deliveryAddresses i_l
          left join i.pictures p
         where i.id = :id
           and i.removed = false
           and (i_l is null or i_l.removed = false)
    """)
    fun findByIdWithProjection(id: UUID): List<ItemProjection>

    @Modifying
    @Query("""
        update Item i
           set i.removed = true
         where i.category.id = :removingCategoryId
           and i.removed = false
    """)
    fun setRemovedOnCategoryItems(removingCategoryId: UUID)

    @Query("""
        from Item i
        join i.pictures p
        where p.id = :id
    """)
    fun getItemsByPictureId(id: UUID): List<Item>
}
