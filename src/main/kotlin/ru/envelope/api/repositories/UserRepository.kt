package ru.envelope.api.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.envelope.api.entities.User

interface UserRepository: JpaRepository<User, Long>
