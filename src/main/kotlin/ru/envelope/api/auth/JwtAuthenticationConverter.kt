package ru.envelope.api.auth

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import ru.envelope.api.services.UserService

@Component
class JwtAuthenticationConverter(
    private val userService: UserService
): Converter<Jwt, AbstractAuthenticationToken> {
    override fun convert(jwt: Jwt): AbstractAuthenticationToken {
        val user = userService.findById(jwt.subject.toLong())
        val authorities = jwt.getClaimAsStringList("roles").map(::SimpleGrantedAuthority)

        return object : AbstractAuthenticationToken(authorities) {
            override fun isAuthenticated() = true
            override fun getCredentials() = user
            override fun getPrincipal() = user
        }
    }
}
