package ru.envelope.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EnvelopeApiApplication

fun main(args: Array<String>) {
	runApplication<EnvelopeApiApplication>(*args)
}
