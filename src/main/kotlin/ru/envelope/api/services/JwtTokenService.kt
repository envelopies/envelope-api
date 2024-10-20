package ru.envelope.api.services

import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import ru.envelope.api.entities.User
import java.time.Instant

@Service
class JwtTokenService(
    private val jwtEncoder: JwtEncoder
) {
    fun generateToken(user: User): String {
        val now = Instant.now()
        val expiry = 3600L

        val header = JwsHeader.with(MacAlgorithm.HS256).build()

        val claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiry))
            .subject(user.id.toString())
            .claim("roles", user.authorities)
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).tokenValue
    }
}
