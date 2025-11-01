package ru.envelope.api.repositories

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.envelope.api.entities.User
import ru.envelope.api.projections.UserProjection
import ru.envelope.api.projections.UserSimpleProjection

interface UserRepository: JpaRepository<User, Long> {
    @Query(value = """
        SELECT u.id AS id,
               u.username AS username,
               u.full_name AS fullname,
               u.verified AS verified,
               a.authority AS authority
          FROM users AS u
          LEFT JOIN authorities AS a ON u.id = a.user_id
    """, countQuery = """
        SELECT COUNT(*)
          FROM users
    """, nativeQuery = true)
    fun findAllWithProjection(page: Pageable): Page<UserProjection>

    @Query("""
        select u.id as id,
               u.fullName as name
          from User u
         where u.verified = true
         order by random()
        """)
    fun findAllVerifiedSellers(): List<UserSimpleProjection>
}
