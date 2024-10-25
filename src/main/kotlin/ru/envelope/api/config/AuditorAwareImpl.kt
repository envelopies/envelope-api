package ru.envelope.api.config

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import ru.envelope.api.entities.User
import java.util.*

/**
 * Компонент, который говорит Spring JPA кто является юзером.
 */
class AuditorAwareImpl: AuditorAware<User> {
    override fun getCurrentAuditor(): Optional<User> {
        val auth = SecurityContextHolder.getContext().authentication

        return if (auth != null && auth.isAuthenticated) {
            Optional.of(auth.principal as User)
        } else {
            Optional.empty()
        }
    }
}
