package ru.envelope.api.config

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "picture")
class PictureConfig {
    /** Максимальный размер картинки */
    @Pattern(regexp = "^\\d+[gmk]?$")
    @NotNull
    lateinit var maxSize: String

    /** Где будут лежать картинки */
    @NotNull
    lateinit var baseDir: String
}
