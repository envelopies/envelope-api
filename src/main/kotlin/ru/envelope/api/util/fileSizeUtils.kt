package ru.envelope.api.util

val sizeRegex = Regex("^(\\d+)(g|m|k)?$", RegexOption.IGNORE_CASE)

fun String.toBytesLength(): Long {
    if (!sizeRegex.matches(this)) {
        // если получено из PictureConfig, то никогда не выкинется
        throw IllegalArgumentException("Size does not match regex")
    }

    val matchGroups = sizeRegex.find(this, 0)!!.groupValues

    // если без символа
    if (matchGroups.count() == 2) {
        return matchGroups[1].toLong()
    } else {
        return when (matchGroups[2]) {
            "k" -> matchGroups[1].toLong() * 1024
            "m" -> matchGroups[1].toLong() * 1024 * 1024
            "g" -> matchGroups[1].toLong() * 1024 * 1024 * 1024
            // никогда не будет вызвано, только если случайно регекс не изменится
            else -> throw IllegalArgumentException("Неизвестная величина размера: ${matchGroups[1]}")
        }
    }
}
