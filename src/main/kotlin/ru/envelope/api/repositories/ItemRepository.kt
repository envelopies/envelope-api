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
               i.created_at AS createdAt,
               u.username AS username,
               c.title AS category
          FROM items i
          JOIN users u ON i.user_id = u.id
          JOIN categories c ON i.category_id = c.id
    """, countQuery = """
        SELECT COUNT(*)
          FROM items
    """, nativeQuery = true)
    fun findAllWithProjection(page: Pageable): Page<ItemProjection>

    @Query("""
        select i.id as id,
               i.title as title,
               i.description as description,
               i.price as price,
               i.createdAt as createdAt,
               u.username as username,
               c.title as category
          from Item i
          join i.createdBy u
          join i.category c
         where i.id = :id
           and i.removed = false
    """
    )
    fun findByIdWithProjection(id: UUID): ItemProjection?

    @Modifying
    @Query("""
        update Item i
           set i.removed = true
         where i.category.id = :removingCategoryId
           and i.removed = false
    """)
    fun setRemovedOnCategoryItems(removingCategoryId: UUID)
}
