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
import org.springframework.core.convert.converter.Converter
import org.springframework.data.domain.AuditorAware
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.web.SecurityFilterChain
import ru.envelope.api.entities.User
import javax.crypto.spec.SecretKeySpec

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class AuthConfig(
    @Value("\${auth.secret}")
    private val secretKey: String,
    private val jwtAuthenticationConverter: Converter<Jwt, AbstractAuthenticationToken>
) {
    private val secretKeySpec = SecretKeySpec(secretKey.toByteArray(), "HmacSHA256")

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain = httpSecurity
        .authorizeHttpRequests { auth ->
            auth.requestMatchers("/swagger-ui.html", "/swagger-ui/*", "/v3/api-docs/**").permitAll()
                .requestMatchers("v1/auth").permitAll()
                .requestMatchers("v1/items", "v1/items/{id}").permitAll()
                .anyRequest().authenticated()
        }
        // TODO включить CORS обратно
        .cors { cors ->
            cors.disable()
        }
        // TODO включить CSRF обратно
        .csrf { csrf ->
            csrf.disable()
        }
        .oauth2ResourceServer { c ->
            c.jwt { jc ->
                jc.jwtAuthenticationConverter(jwtAuthenticationConverter)
            }
        }
        .build()

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build()
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = OctetSequenceKey.Builder(secretKeySpec)
            .algorithm(JWSAlgorithm.HS256)
            .build()

        val jwkSource: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return NimbusJwtEncoder(jwkSource)
    }

    @Bean
    fun auditorProvider(): AuditorAware<User> {
        return AuditorAwareImpl()
    }
}
