package ru.envelope.api.config

import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@ConfigurationProperties(prefix = "cors")
class CorsConfigurationBuilder {
    @NotNull
    lateinit var allowedOrigins: Array<String>

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        for (origin in allowedOrigins) {
            corsConfiguration.addAllowedOrigin(origin)
        }
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL)
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL)
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source
    }
}
