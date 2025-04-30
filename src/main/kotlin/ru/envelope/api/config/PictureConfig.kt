package ru.envelope.api.config

import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "picture")
class PictureConfig {
    /** Где будут лежать картинки */
    @NotNull
    lateinit var baseDir: String
}
