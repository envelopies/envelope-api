package ru.envelope.api.config

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.OctetSequenceKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import javax.crypto.spec.SecretKeySpec

@Configuration
@EnableWebSecurity
class AuthConfig(
    @Value("\${auth.secret}")
    private val secretKey: String
) {
    private val secretKeySpec = SecretKeySpec(secretKey.toByteArray(), "HmacSHA256")

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = OctetSequenceKey.Builder(secretKeySpec)
            .algorithm(JWSAlgorithm.HS256)
            .build()

        val jwkSource: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return NimbusJwtEncoder(jwkSource)
    }
}
