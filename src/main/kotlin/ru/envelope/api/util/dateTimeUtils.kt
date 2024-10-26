package ru.envelope.api.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val localDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

fun Instant.format(): String = this.atZone(ZoneId.systemDefault()).format(localDateTimeFormatter)