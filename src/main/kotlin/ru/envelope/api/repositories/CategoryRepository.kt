package ru.envelope.api.repositories

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.envelope.api.entities.Category
import ru.envelope.api.projections.CategoryProjection
import java.util.UUID

interface CategoryRepository : JpaRepository<Category, UUID> {
    @Query(value = """
        SELECT c.id AS id,
               c.title AS title,
               c.icon_url AS iconUrl,
               c.parent_category_id AS parentCategoryId
          FROM categories c
    """, countQuery = """
        SELECT COUNT(*)
          FROM categories c
    """, nativeQuery = true)
    fun findAllWithProjection(page: Pageable): Page<CategoryProjection>

    @Query(
        """
        select c.id as id,
               c.title as title,
               c.iconUrl as iconUrl,
               c.parentCategory.id as parentCategoryId
          from Category c
         where c.id = :id
    """
    )
    fun findByIdWithProjection(id: UUID): CategoryProjection?
}
