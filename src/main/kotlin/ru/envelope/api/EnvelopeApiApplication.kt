package ru.envelope.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class EnvelopeApiApplication

fun main(args: Array<String>) {
	runApplication<EnvelopeApiApplication>(*args)
}
