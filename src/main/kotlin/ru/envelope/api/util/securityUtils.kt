package ru.envelope.api.util

import ru.envelope.api.entities.User

fun isAdminOrSpecificId(user: User, id: Long): Boolean = user.id == id || user.authorities.any { it == "ROLE_ADMIN" }
